package io.heidou.codesearch.settings;

import com.intellij.util.ui.ListTableModel;
import io.heidou.codesearch.model.SearchService;

/**
 * Search service table model
 * @author guojianhua
 * @since 2019-04-11
 */
public class SearchServiceTableModel extends ListTableModel<SearchService> {
    public SearchServiceTableModel() {
        super(new IconColumnInfo(), new NameColumnInfo(), new DescriptionColumnInfo(),
                new UrlColumnInfo());
    }
}
