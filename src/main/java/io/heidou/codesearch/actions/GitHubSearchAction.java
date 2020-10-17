package io.heidou.codesearch.actions;

import io.heidou.codesearch.util.IconUtil;

/**
 * Search with GitHub
 *
 * @author guojianhua
 * @since 2020-10-17
 */
public class GitHubSearchAction extends SearchAction {
    private static final String GITHUB_URL = "https://github.com/search?q=";

    public GitHubSearchAction() {
        super("Search with GitHub", "Search with GitHub", IconUtil.GITHUB, GITHUB_URL);
    }
}
