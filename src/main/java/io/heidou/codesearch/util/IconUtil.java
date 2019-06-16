package io.heidou.codesearch.util;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.util.text.StringUtil;

import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;

/**
 * Icon utility class
 * @author guojianhua
 * @since 2019-04-21
 */
public class IconUtil {
    private static final Logger LOG = Logger.getInstance(IconUtil.class);

    public static final Icon BAIDU = IconLoader.getIcon("/icons/baidu.png");
    public static final Icon GOOGLE = IconLoader.getIcon("/icons/google.png");
    public static final Icon STACKOVERFLOW = IconLoader.getIcon("/icons/stackoverflow.png");

    private IconUtil() {
    }

    public static Icon getIcon(String iconPath) {
        try {
            if (!StringUtil.isEmptyOrSpaces(iconPath)) {
                final File iconFile = new File(iconPath);
                if (iconFile.exists() && iconFile.isFile()) {
                    return IconLoader.findIcon(iconFile.toURI().toURL());
                }
            }
        } catch (MalformedURLException e) {
            LOG.warn(e.getMessage());
        }

        return null;
    }
}
