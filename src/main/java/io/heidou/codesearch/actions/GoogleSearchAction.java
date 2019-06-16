package io.heidou.codesearch.actions;

import io.heidou.codesearch.util.IconUtil;

/**
 * Search with Google
 * @author guojianhua
 * @since 2019-04-21
 */
public class GoogleSearchAction extends SearchAction {
    private static final String GOOGLE_URL = "https://www.google.com/search?q=";

    public GoogleSearchAction() {
        super("Search with Google", "Search with Google", IconUtil.GOOGLE, GOOGLE_URL);
    }
}
