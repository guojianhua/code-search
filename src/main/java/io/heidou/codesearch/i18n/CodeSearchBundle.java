package io.heidou.codesearch.i18n;

import com.intellij.CommonBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.ResourceBundle;

/**
 * Code search bundle
 *
 * @author guojianhua
 * @since 2019-04-07
 */
public class CodeSearchBundle {
    private static Reference<ResourceBundle> codeSearchBundle;
    private static final String BUNDLE = "messages.CodeSearchBundle";

    private CodeSearchBundle() {
    }

    public static String message(@NotNull @PropertyKey(resourceBundle = BUNDLE) String key, @NotNull Object... params) {
        return CommonBundle.message(getBundle(), key, params);
    }

    private static ResourceBundle getBundle() {
        ResourceBundle bundle = com.intellij.reference.SoftReference.dereference(codeSearchBundle);
        if (null == bundle) {
            bundle = ResourceBundle.getBundle(BUNDLE);
            codeSearchBundle = new SoftReference<ResourceBundle>(bundle);
        }
        return bundle;
    }
}
