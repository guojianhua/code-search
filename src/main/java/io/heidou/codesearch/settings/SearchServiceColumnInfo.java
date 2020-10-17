package io.heidou.codesearch.settings;

import com.intellij.openapi.util.Comparing;
import com.intellij.util.ui.ColumnInfo;
import io.heidou.codesearch.model.SearchService;
import org.jetbrains.annotations.Nullable;

import javax.swing.JTable;
import java.util.Comparator;

/**
 * Search service column
 *
 * @author guojianhua
 * @since 2019-04-12
 */
public abstract class SearchServiceColumnInfo extends ColumnInfo<SearchService, String> {
    private final int myWidth;

    public SearchServiceColumnInfo(String name, int width) {
        super(name);
        myWidth = width;
    }

    public SearchServiceColumnInfo(String name) {
        this(name, -1);
    }

    @Nullable
    @Override
    public Comparator<SearchService> getComparator() {
        return (o1, o2) -> {
            final String s1 = valueOf(o1);
            final String s2 = valueOf(o2);
            return Comparing.compare(s1, s2);
        };
    }

    @Override
    public int getWidth(JTable table) {
        return myWidth;
    }
}
