package io.heidou.codesearch.util;

import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Url util
 *
 * @author guojianhua
 * @since 2020-10-17
 */
public class UrlUtils {
    private UrlUtils() {
    }

    /**
     * Compatibility with 2016.1+
     * Encodes a URI component by replacing each instance of certain characters by one, two, three,
     * or four escape sequences representing the UTF-8 encoding of the character.
     * Behaves similarly to standard JavaScript build-in function <a href="https://developer.mozilla.org/en/docs/Web/JavaScript/Reference/Global_Objects/encodeURIComponent">encodeURIComponent</a>.
     *
     * @param str a component of a URI
     * @return a new string representing the provided string encoded as a URI component
     */
    @NotNull
    public static String encodeURIComponent(@NotNull String str) {
        try {
            return URLEncoder.encode(str, StandardCharsets.UTF_8.name())
                    .replace("+", "%20")
                    .replace("%21", "!")
                    .replace("%27", "'")
                    .replace("%28", "(")
                    .replace("%29", ")")
                    .replace("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }
}
