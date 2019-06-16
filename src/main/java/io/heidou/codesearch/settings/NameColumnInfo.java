package io.heidou.codesearch.settings;

import io.heidou.codesearch.i18n.CodeSearchBundle;
import io.heidou.codesearch.model.SearchService;
import org.jetbrains.annotations.Nullable;

/**
 * Name column
 * @author guojianhua
 * @since 2019-04-12
 */
public class NameColumnInfo extends SearchServiceColumnInfo {
    public NameColumnInfo() {
        super(CodeSearchBundle.message("name.column.name"), -1);
    }

    @Nullable
    @Override
    public String valueOf(SearchService searchService) {
        return searchService.getName();
    }
}
