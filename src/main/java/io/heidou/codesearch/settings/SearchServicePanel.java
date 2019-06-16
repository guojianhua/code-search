package io.heidou.codesearch.settings;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.OnePixelDivider;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.DoubleClickListener;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.ui.table.TableView;
import com.intellij.util.IconUtil;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.ListTableModel;
import com.intellij.util.ui.UIUtil;
import io.heidou.codesearch.i18n.CodeSearchBundle;
import io.heidou.codesearch.model.SearchService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Search service panel
 * @author guojianhua
 * @since 2019-04-11
 */
public class SearchServicePanel extends JPanel {
    private TableView<SearchService> searchServiceTable;
    private ListTableModel<SearchService> searchServiceTableModel = new SearchServiceTableModel();

    public SearchServicePanel() {
        this.setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(JBUI.Borders.customLine(OnePixelDivider.BACKGROUND, 1, 0, 1, 0),
                CodeSearchBundle.message("custom.search.service.desc")));

        searchServiceTableModel.setSortable(true);
        searchServiceTable = new TableView<>();
        searchServiceTable.setModelAndUpdateColumns(searchServiceTableModel);
        searchServiceTable.setAutoCreateRowSorter(true);
        searchServiceTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        new DoubleClickListener() {
            @Override
            protected boolean onDoubleClick(MouseEvent e) {
                showSearchServiceDialog();
                return true;
            }
        }.installOn(searchServiceTable);

        ToolbarDecorator decorator = ToolbarDecorator.createDecorator(searchServiceTable);
        decorator.disableAddAction();
        decorator.disableRemoveAction();
        decorator.disableDownAction();
        decorator.disableUpAction();

        decorator.addExtraAction(new AddSearchServiceAction(SearchServicePanel.this));
        decorator.addExtraAction(new DeleteSearchServiceAction(SearchServicePanel.this));
        decorator.addExtraAction(new EditSearchServiceAction(SearchServicePanel.this));

        add(decorator.createPanel(), BorderLayout.CENTER);

        refreshSearchServices();
    }

    public void refreshSearchServices() {
        final List<SearchService> searchServiceList = SearchServiceSettings.getInstance().getSearchServiceList();
        UIUtil.invokeLaterIfNeeded(() -> {
            searchServiceTableModel.setItems(searchServiceList);
        });
    }

    private SearchService getSelectedSearchService() {
        return searchServiceTable.getSelectedObject();
    }

    private void showSearchServiceDialog() {
        final SearchService selectedSearchService = getSelectedSearchService();
        if (selectedSearchService == null) {
            return;
        }

        SearchServiceDialog searchServiceDialog = new SearchServiceDialog(null,
                selectedSearchService, SearchServicePanel.this);
        searchServiceDialog.show();
    }

    private class AddSearchServiceAction extends AnActionButton {
        private SearchServicePanel searchServicePanel;

        public AddSearchServiceAction(SearchServicePanel searchServicePanel) {
            super(CodeSearchBundle.message("add.action.name"), IconUtil.getAddIcon());
            this.searchServicePanel = searchServicePanel;
        }

        @Override
        public void actionPerformed(AnActionEvent e) {
            SearchServiceDialog searchServiceDialog = new SearchServiceDialog(null, null,
                    searchServicePanel);
            searchServiceDialog.show();
        }
    }

    private class DeleteSearchServiceAction extends AnActionButton {
        private SearchServicePanel searchServicePanel;

        public DeleteSearchServiceAction(SearchServicePanel searchServicePanel) {
            super(CodeSearchBundle.message("remove.action.name"), IconUtil.getRemoveIcon());
            this.searchServicePanel = searchServicePanel;
        }

        @Override
        public void actionPerformed(AnActionEvent e) {
            final int result = Messages.showYesNoDialog(CodeSearchBundle.message("remove.message"),
                    CodeSearchBundle.message("remove.title"), Messages.getWarningIcon());
            if (result == Messages.YES) {
                final SearchService selectedSearchService = searchServicePanel.getSelectedSearchService();
                SearchServiceSettings.getInstance().removeSearchService(selectedSearchService.getName());
                searchServicePanel.refreshSearchServices();
            }
        }

        @Override
        public void updateButton(AnActionEvent e) {
            super.updateButton(e);
            final SearchService selectedSearchService = searchServicePanel.getSelectedSearchService();
            e.getPresentation().setEnabled(selectedSearchService != null);
        }
    }

    private class EditSearchServiceAction extends AnActionButton {
        private SearchServicePanel searchServicePanel;

        public EditSearchServiceAction(SearchServicePanel searchServicePanel) {
            super(CodeSearchBundle.message("edit.action.name"), IconUtil.getEditIcon());
            this.searchServicePanel = searchServicePanel;
        }

        @Override
        public void actionPerformed(AnActionEvent e) {
            showSearchServiceDialog();
        }

        @Override
        public void updateButton(AnActionEvent e) {
            super.updateButton(e);
            final SearchService selectedSearchService = searchServicePanel.getSelectedSearchService();
            e.getPresentation().setEnabled(selectedSearchService != null);
        }
    }
}
