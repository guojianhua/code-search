package io.heidou.codesearch.actions;

import io.heidou.codesearch.util.IconUtil;

/**
 * Search with Baidu
 * @author guojianhua
 * @since 2019-04-21
 */
public class BaiduSearchAction extends SearchAction {
    private static final String BAIDU_URL = "https://www.baidu.com/s?wd=";

    public BaiduSearchAction() {
        super("Search with Baidu", "Search with Baidu", IconUtil.BAIDU, BAIDU_URL);
    }
}
