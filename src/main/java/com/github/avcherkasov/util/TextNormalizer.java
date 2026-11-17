package com.github.avcherkasov.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextNormalizer {

    public static final int DEFAULT_MAX_CHARS = 200;
    public static final String SPLIT_PATTERN = "\\s";
    public static final Pattern ALPHABET_ONLY_PATTERN = Pattern.compile("[^A-Za-z\\s]");
    public static final Pattern PREFIX_TITLE_PATTERN = Pattern.compile("^(Mr|Ms|Madam)\\.?\\s*", Pattern.CASE_INSENSITIVE);

    public static String normalizeAndSortWords(String text) {
        return normalizeAndSortWords(text, DEFAULT_MAX_CHARS);
    }

    public static String normalizeAndSortWords(String text, int maxChars) {
        if (text == null || text.isBlank() || maxChars <= 0) {
            return StringUtils.EMPTY;
        }

        var truncated = truncated(text, maxChars);
        var cleaned = cleanText(truncated);

        return Arrays.stream(cleaned.toLowerCase().split(SPLIT_PATTERN))
                .filter(word -> !word.isEmpty())
                .sorted()
                .collect(Collectors.joining(StringUtils.EMPTY));
    }

    private static String cleanText(String text) {
        var matcher = PREFIX_TITLE_PATTERN.matcher(text);
        var withoutTitle = matcher.lookingAt()
                ? text.substring(matcher.end())
                : text;
        return ALPHABET_ONLY_PATTERN.matcher(withoutTitle).replaceAll(StringUtils.EMPTY);
    }

    private static String truncated(String text, int maxChars) {
        return text.length() > maxChars
                ? text.substring(0, maxChars)
                : text;
    }

}
