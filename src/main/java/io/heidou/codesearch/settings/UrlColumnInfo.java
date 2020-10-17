package io.heidou.codesearch.settings;

import io.heidou.codesearch.i18n.CodeSearchBundle;
import io.heidou.codesearch.model.SearchService;
import org.jetbrains.annotations.Nullable;

/**
 * Url column
 *
 * @author guojianhua
 * @since 2019-04-12
 */
public class UrlColumnInfo extends SearchServiceColumnInfo {
    public UrlColumnInfo() {
        super(CodeSearchBundle.message("url.column.name"));
    }

    @Nullable
    @Override
    public String valueOf(SearchService searchService) {
        return searchService.getUrl();
    }
}
