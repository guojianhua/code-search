package io.heidou.codesearch.settings;

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.progress.impl.ProgressManagerImpl;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.SimpleColoredComponent;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.util.io.HttpRequests;
import io.heidou.codesearch.i18n.CodeSearchBundle;
import io.heidou.codesearch.model.SearchService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Add or edit search service dialog
 * @author guojianhua
 * @since 2019-04-14
 */
public class SearchServiceDialog extends DialogWrapper {
    // 3 milliseconds
    private static final int READ_TIMEOUT = 3 * 1000;

    private static final String ASTERISK = "*";

    private static final SimpleTextAttributes REQUIRED_ATTRS = SimpleTextAttributes.ERROR_ATTRIBUTES;

    private Project myProject;
    private SearchService mySearchService;
    private SearchServicePanel mySearchServicePanel;
    private boolean isAdd;

    private JPanel contentPanel;
    private JTextField nameTextField;
    private JTextField descriptionTextField;
    private JTextField urlTextField;
    private JButton checkButton;
    private TextFieldWithBrowseButton iconPathTextField;
    private SimpleColoredComponent nameLabel;
    private SimpleColoredComponent descriptionLabel;
    private SimpleColoredComponent urlLabel;
    private SimpleColoredComponent iconLabel;

    public SearchServiceDialog(@Nullable Project project, @Nullable SearchService searchService,
                               @NotNull SearchServicePanel searchServicePanel) {
        super(project);

        myProject = project;
        mySearchService = searchService;
        this.mySearchServicePanel = searchServicePanel;
        isAdd = (null == searchService);
        final String title = isAdd ? CodeSearchBundle.message("add.title") : CodeSearchBundle.message("edit.title");
        setTitle(title);
        setResizable(false);

        final String okButtonText = isAdd ? CodeSearchBundle.message("add") : CodeSearchBundle.message("edit");
        setOKButtonText(okButtonText);
        init();
    }

    @Override
    public void init() {
        super.init();

        nameLabel.append("Name: ");
        nameLabel.append(ASTERISK, REQUIRED_ATTRS);

        descriptionLabel.append("Description: ");

        urlLabel.append("Url: ");
        urlLabel.append(ASTERISK, REQUIRED_ATTRS);

        iconLabel.append("Icon Path: ");

        nameTextField.setToolTipText(CodeSearchBundle.message("name.tooltip"));
        descriptionTextField.setToolTipText(CodeSearchBundle.message("desc.tooltip"));
        iconPathTextField.getTextField().setToolTipText(CodeSearchBundle.message("icon.tooltip"));

        initData();

        addListener();
    }

    private void initData() {
        if (!isAdd) {
            nameTextField.setText(mySearchService.getName());
            descriptionTextField.setText(mySearchService.getDescription());
            urlTextField.setText(mySearchService.getUrl());
            iconPathTextField.setText(mySearchService.getIconPath());
        }
    }

    private void addListener() {
        iconPathTextField.addBrowseFolderListener(CodeSearchBundle.message("config.title"),
                CodeSearchBundle.message("icon.config.desc"),
                myProject, FileChooserDescriptorFactory.createSingleFileDescriptor("png"));

        checkButton.addActionListener(e -> {
            final String url = getUrl();
            if (StringUtil.isEmptyOrSpaces(url)) {
                return;
            }

            final String title = CodeSearchBundle.message("check.conn.title");
            final AtomicReference<IOException> exceptionReference = new AtomicReference<>();
            ProgressManager.getInstance().runProcessWithProgressSynchronously(() -> {
                try {
                    HttpRequests.request(url).readTimeout(READ_TIMEOUT).tryConnect();
                } catch (IOException e1) {
                    exceptionReference.set(e1);
                }
            }, title, true, null);

            final IOException exception = exceptionReference.get();
            if (exception == null) {
                Messages.showMessageDialog(contentPanel, CodeSearchBundle.message("conn.success.msg"), title,
                        Messages.getInformationIcon());
            } else {
                final String message = exception.getMessage();
                Messages.showErrorDialog(contentPanel, CodeSearchBundle.message("conn.fail.msg", message));
            }
        });
    }

    @Nullable
    @Override
    public JComponent createCenterPanel() {
        return contentPanel;
    }

    @Nullable
    @Override
    public ValidationInfo doValidate() {
        String message = null;
        final String name = getName();
        if (StringUtil.isEmptyOrSpaces(getName())) {
            message = CodeSearchBundle.message("name.required");
        } else if (StringUtil.isEmptyOrSpaces(getUrl())) {
            message = CodeSearchBundle.message("url.required");
        }

        return message != null ? new ValidationInfo(message) : null;
    }

    @Override
    public void doOKAction() {
        final ValidationInfo validationInfo = doValidate();
        if (validationInfo != null) {
            return;
        }

        if (existsName()) {
            return;
        }

        final SearchService newSearchService = getSearchService();
        addOrEditTask(newSearchService);

        super.doOKAction();
    }

    private boolean existsName() {
        final Map<String, SearchService> searchServiceMap = SearchServiceSettings.getInstance().getSearchServiceMap();
        final Map<String, SearchService> mySearchServiceMap = new HashMap<>(searchServiceMap);
        if (!isAdd) {
            mySearchServiceMap.remove(mySearchService.getName());
        }

        final String name = getName();
        if (mySearchServiceMap.get(name) != null) {
            final String message = CodeSearchBundle.message("name.exists", name);
            final String title =
                    isAdd ? CodeSearchBundle.message("add.title") : CodeSearchBundle.message("edit.title");
            Messages.showWarningDialog(message, title);
            return true;
        }
        return false;
    }

    private void addOrEditTask(final @NotNull SearchService newSearchService) {
        final String title =
                isAdd ? CodeSearchBundle.message("add.task.title") : CodeSearchBundle.message("edit.task.title");
        final Task task = new Task.Backgroundable(null, title) {
            @Override
            public void run(@NotNull ProgressIndicator progressIndicator) {
                if (!isAdd) {
                    SearchServiceSettings.getInstance().removeSearchService(mySearchService.getName());
                }

                SearchServiceSettings.getInstance().setSearchService(newSearchService.getName(), newSearchService);

                mySearchServicePanel.refreshSearchServices();
            }
        };

        ProgressManagerImpl.getInstance().run(task);
    }

    private SearchService getSearchService() {
        final SearchService searchService = new SearchService();
        searchService.setName(getName());
        searchService.setDescription(getDescription());
        searchService.setUrl(getUrl());
        searchService.setIconPath(getIconPath());
        return searchService;
    }

    private String getName() {
        final String name = nameTextField.getText();
        return StringUtil.isEmptyOrSpaces(name) ? "" : name.trim();
    }

    private String getDescription() {
        final String description = descriptionTextField.getText();
        return StringUtil.isEmptyOrSpaces(description) ? "" : description.trim();
    }

    private String getUrl() {
        final String url = urlTextField.getText();
        return StringUtil.isEmptyOrSpaces(url) ? "" : url.trim();
    }

    private String getIconPath() {
        return iconPathTextField.getText().trim();
    }
}
