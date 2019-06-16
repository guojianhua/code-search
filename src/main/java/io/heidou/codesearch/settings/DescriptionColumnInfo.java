package io.heidou.codesearch.settings;

import io.heidou.codesearch.i18n.CodeSearchBundle;
import io.heidou.codesearch.model.SearchService;
import org.jetbrains.annotations.Nullable;

/**
 * Description column
 * @author guojianhua
 * @since 2019-04-12
 */
public class DescriptionColumnInfo extends SearchServiceColumnInfo {
    public DescriptionColumnInfo() {
        super(CodeSearchBundle.message("desc.column.name"), -1);
    }

    @Nullable
    @Override
    public String valueOf(SearchService searchService) {
        return searchService.getDescription();
    }
}
