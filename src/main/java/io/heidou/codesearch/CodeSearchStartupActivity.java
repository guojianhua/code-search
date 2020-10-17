package io.heidou.codesearch;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.Anchor;
import com.intellij.openapi.actionSystem.Constraints;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.Separator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.util.text.StringUtil;
import io.heidou.codesearch.actions.SearchAction;
import io.heidou.codesearch.model.SearchService;
import io.heidou.codesearch.settings.SearchServiceSettings;
import io.heidou.codesearch.util.IconUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;
import java.util.List;

/**
 * Code search startup activity
 *
 * @author guojianhua
 * @since 2020-07-05
 */
public class CodeSearchStartupActivity implements StartupActivity {
    private volatile boolean registered = false;

    private static final String CODE_SEARCH_ACTION_GROUP_ID = "HeiDou.CodeSearch";
    private static final String CUSTOM_SEARCH_ACTION_GROUP_NAME = "Custom Search Group";
    private static final String CUSTOM_SEARCH_ACTION_ID = "HeiDou.CodeSearch.CustomSearch";

    @Override
    public void runActivity(@NotNull Project project) {
        if (!registered) {
            addCustomSearchServiceAction();
            registered = true;
        }
    }

    private void addCustomSearchServiceAction() {
        final ActionManager actionManager = ActionManager.getInstance();
        final AnAction codeSearchMenu = actionManager.getAction(CODE_SEARCH_ACTION_GROUP_ID);
        if (!(codeSearchMenu instanceof DefaultActionGroup)) {
            return;
        }

        final DefaultActionGroup codeSearchGroup = ((DefaultActionGroup) codeSearchMenu);
        final DefaultActionGroup customActionGroup = new DefaultActionGroup(CUSTOM_SEARCH_ACTION_GROUP_NAME, false);
        final List<SearchService> searchServiceList = SearchServiceSettings.getInstance().getSearchServiceList();
        for (SearchService searchService : searchServiceList) {
            final String name = searchService.getName();
            final String url = searchService.getUrl();
            if (StringUtil.isEmptyOrSpaces(name) || StringUtil.isEmptyOrSpaces(url)) {
                continue;
            }

            String description = searchService.getDescription();
            description = StringUtil.isEmptyOrSpaces(description) ? "" : description.trim();
            final Icon icon = IconUtil.getIcon(searchService.getIconPath());
            customActionGroup.add(new SearchAction(name.trim(), description, icon, url.trim()));
        }

        customActionGroup.add(new Separator());

        codeSearchGroup.add(customActionGroup, new Constraints(Anchor.BEFORE, CUSTOM_SEARCH_ACTION_ID));
    }
}
