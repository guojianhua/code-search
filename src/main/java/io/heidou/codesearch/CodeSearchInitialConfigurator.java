package io.heidou.codesearch;

import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.extensions.ExtensionPointName;
import org.jetbrains.annotations.NotNull;

/**
 * Initial Configurator
 * @author guojianhua
 * @since 2019-03-25
 */
public class CodeSearchInitialConfigurator implements ApplicationComponent {
    private static final ExtensionPointName<Runnable> EP_NAME =
            ExtensionPointName.create("io.heidou.codesearch.CodeSearchInitializer");

    @Override
    public void initComponent() {
        activateInitialExtensions();
    }

    @Override
    public void disposeComponent() {
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "CodeSearch Startup Extension Point";
    }

    private void activateInitialExtensions() {
        final Runnable[] extensions = EP_NAME.getExtensions();
        for (Runnable r : extensions) {
            r.run();
        }
    }
}
