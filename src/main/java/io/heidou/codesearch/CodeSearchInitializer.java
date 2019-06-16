package io.heidou.codesearch;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.util.text.StringUtil;
import io.heidou.codesearch.actions.SearchAction;
import io.heidou.codesearch.model.SearchService;
import io.heidou.codesearch.settings.SearchServiceSettings;
import io.heidou.codesearch.util.IconUtil;

import javax.swing.*;
import java.util.List;

/**
 * Initializer
 * @author guojianhua
 * @since 2019-03-25
 */
public class CodeSearchInitializer implements Runnable {
    private static final String CODE_SEARCH_ACTION_GROUP_ID = "HeiDou.CodeSearch";
    private static final String CODE_SEARCH_ACTION_GROUP_NAME = "Code Search";

    private static final String BAIDU_ACIION_ID = "HeiDou.CodeSearch.Baidu";
    private static final String GOOGLE_ACIION_ID = "HeiDou.CodeSearch.Google";
    private static final String STACKOVERFLOW_ACIION_ID = "HeiDou.CodeSearch.StackOverflow";

    private static final String CUSTOM_SEARCH_ACTION_ID = "HeiDou.CodeSearch.CustomSearch";

    @Override
    public void run() {
        findOrCreateCodeSearchMenuGroup();
    }

    private void findOrCreateCodeSearchMenuGroup() {
        final ActionManager actionManager = ActionManager.getInstance();
        AnAction codeSearchMenu = actionManager.getAction(CODE_SEARCH_ACTION_GROUP_ID);
        if (null == codeSearchMenu) {
            codeSearchMenu = new DefaultActionGroup(CODE_SEARCH_ACTION_GROUP_NAME, true);
            actionManager.registerAction(CODE_SEARCH_ACTION_GROUP_ID, codeSearchMenu);

            final AnAction editorPopupMenu = actionManager.getAction(IdeActions.GROUP_EDITOR_POPUP);
            if (editorPopupMenu instanceof DefaultActionGroup) {
                ((DefaultActionGroup) editorPopupMenu).add(codeSearchMenu, Constraints.FIRST);
            }
        }

        if (codeSearchMenu instanceof DefaultActionGroup) {
            final DefaultActionGroup codeSearchGroup = ((DefaultActionGroup) codeSearchMenu);
            // add default search service action
            codeSearchGroup.add(actionManager.getAction(BAIDU_ACIION_ID));
            codeSearchGroup.add(actionManager.getAction(GOOGLE_ACIION_ID));
            codeSearchGroup.add(actionManager.getAction(STACKOVERFLOW_ACIION_ID));
            codeSearchGroup.add(new Separator());

            // add custom search service action
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
                codeSearchGroup.add(new SearchAction(name.trim(), description, icon, url.trim()));
            }

            codeSearchGroup.add(new Separator());

            // add custom search action
            codeSearchGroup.add(actionManager.getAction(CUSTOM_SEARCH_ACTION_ID), Constraints.LAST);
        }
    }
}
