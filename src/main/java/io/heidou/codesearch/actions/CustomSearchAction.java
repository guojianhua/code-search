package io.heidou.codesearch.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import io.heidou.codesearch.i18n.CodeSearchBundle;
import io.heidou.codesearch.settings.CodeSearchConfigurable;

/**
 * Custom search action
 * @author guojianhua
 * @since 2019-03-25
 */
public class CustomSearchAction extends AnAction {
    public CustomSearchAction() {
        super(CodeSearchBundle.message("custom.search.action.name"));
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        final Project project = e.getProject();
        ShowSettingsUtil.getInstance().showSettingsDialog(project, CodeSearchConfigurable.class);
    }
}
