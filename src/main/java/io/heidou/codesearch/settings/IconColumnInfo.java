package io.heidou.codesearch.settings;

import com.intellij.ui.components.JBLabel;
import com.intellij.util.ui.ColumnInfo;
import io.heidou.codesearch.i18n.CodeSearchBundle;
import io.heidou.codesearch.model.SearchService;
import io.heidou.codesearch.util.IconUtil;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;

/**
 * Icon column
 *
 * @author guojianhua
 * @since 2019-04-11
 */
public class IconColumnInfo extends ColumnInfo<SearchService, Icon> {
    private static final int WIDTH = 50;

    public IconColumnInfo() {
        super(CodeSearchBundle.message("icon.column.name"));
    }

    @Nullable
    @Override
    public Icon valueOf(SearchService searchService) {
        return IconUtil.getIcon(searchService.getIconPath());
    }

    @Override
    public int getWidth(JTable table) {
        return WIDTH;
    }

    @Nullable
    @Override
    public TableCellRenderer getRenderer(SearchService searchService) {
        return ourIconRenderer;
    }

    /**
     * Renders an icon in a small square field
     */
    private static final TableCellRenderer ourIconRenderer = new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            JBLabel label = new JBLabel((Icon)value);
            if (table.getSelectedRow() == row) {
                label.setBackground(table.getSelectionBackground());
                label.setForeground(table.getSelectionForeground());
                label.setOpaque(true);
            }

            return label;
        }
    };
}
