package io.heidou.codesearch.actions;

import io.heidou.codesearch.util.IconUtil;

/**
 * Search with StackOverflow
 *
 * @author guojianhua
 * @since 2019-04-21
 */
public class StackOverflowSearchAction extends SearchAction {
    private static final String STACKOVERFLOW_URL = "https://stackoverflow.com/search?q=";

    public StackOverflowSearchAction() {
        super("Search with StackOverflow", "Search with StackOverflow", IconUtil.STACKOVERFLOW, STACKOVERFLOW_URL);
    }
}
