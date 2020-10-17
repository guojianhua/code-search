package io.heidou.codesearch.actions;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import io.heidou.codesearch.util.UrlUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

/**
 * Search base class
 *
 * @author guojianhua
 * @since 2019-04-21
 */
public class SearchAction extends AnAction {
    private final String myUrl;

    public SearchAction(@NotNull String text, @Nullable String description, @Nullable Icon icon,
            @NotNull String url) {
        super(text, description, icon);
        this.myUrl = url;
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Project project = e.getProject();
        if (null == project || !project.isInitialized() || project.isDisposed()) {
            return;
        }

        final Editor editor = CommonDataKeys.EDITOR.getData(e.getDataContext());
        String selectedText = editor != null ? editor.getSelectionModel().getSelectedText() : "";
        selectedText = StringUtil.isEmptyOrSpaces(selectedText) ? "" : selectedText.trim();
        BrowserUtil.browse(myUrl + UrlUtils.encodeURIComponent(selectedText));
    }
}
