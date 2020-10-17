package io.heidou.codesearch.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import io.heidou.codesearch.i18n.CodeSearchBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.BorderLayout;

/**
 * Code search setting
 *
 * @author guojianhua <781377344@qq.com>
 * @since 2019-04-07
 */
public class CodeSearchConfigurable implements SearchableConfigurable, Configurable.NoScroll {
    private static final String ID = "HeiDou.CodeSearch.Settings";

    @NotNull
    @Override
    public String getId() {
        return ID;
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return CodeSearchBundle.message("codesearch.settings.title");
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        final JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BorderLayout());

        final SearchServicePanel searchServicePanel = new SearchServicePanel();
        rootPanel.add(searchServicePanel, BorderLayout.CENTER);
        return rootPanel;
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {
    }

    @Override
    public Runnable enableSearch(String option) {
        return null;
    }

    @Override
    public String getHelpTopic() {
        return null;
    }

    @Override
    public void reset() {
    }

    @Override
    public void disposeUIResources() {
    }
}
