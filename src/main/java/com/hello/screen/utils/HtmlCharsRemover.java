package com.hello.screen.utils;

public class HtmlCharsRemover {
    private static final String HTML_CHARS_REPLACE_REGEX = "&quot;|&amp;|&lt;|&gt;|&deg;|&frasl;|&oacute;";

    /**
     * remove SPECIFIED html special characters and entities from given string
     * the regex used to replace is stored in constant "HTML_CHARS_REPLACE_REGEX"
     *
     * @return string with HTML special chars replaced as spaces
     */
    public static String removeSpecials(String dirtyText) {
        return dirtyText.replaceAll(HTML_CHARS_REPLACE_REGEX, " ");
    }

    public static String replaceEntities(String dirtyText) {
        return dirtyText.replace("&lt;", "<")
                .replace("&amp;", "&")
                .replace("&quot;", "\"")
                .replace("&gt;", ">")
                .replace("&apos;", "'");
    }
}
