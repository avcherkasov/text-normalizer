package com.github.avcherkasov.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextNormalizerTest {

    static Stream<Arguments> cardholderArgs() {
      return Stream.of(
              Arguments.of("Петров Василий Васильевич", ""),
              Arguments.of("Halif Alibaba Alibabaevich", "alibabaalibabaevichhalif"),
              Arguments.of("Halif Alibabaevich Alibaba", "alibabaalibabaevichhalif"),
              Arguments.of("Alibaba Alibabaevich Halif", "alibabaalibabaevichhalif"),
              Arguments.of("Mr Halif Alibaba Alibabaevich", "alibabaalibabaevichhalif"),
              Arguments.of("    multiple    spaces    here    ", "heremultiplespaces"),
              Arguments.of("", ""),
              Arguments.of(null, "")
      );
    }

    @ParameterizedTest(name = "{0} -> {1}")
    @MethodSource("cardholderArgs")
    void normalizeAndSortWords(String input, String expected) {
        assertEquals(expected, TextNormalizer.normalizeAndSortWords(input));
    }

    @Test
    void cutsToLimitBeforeWordProcessing() {
        assertEquals("bellcu", TextNormalizer.normalizeAndSortWords("Ms Bell Curves", 10));
    }

    @Test
    void returnEmptyOnInvalidInput() {
        assertEquals(StringUtils.EMPTY, TextNormalizer.normalizeAndSortWords(null, TextNormalizer.DEFAULT_MAX_CHARS));
        assertEquals(StringUtils.EMPTY, TextNormalizer.normalizeAndSortWords("    ", TextNormalizer.DEFAULT_MAX_CHARS));
        assertEquals(StringUtils.EMPTY, TextNormalizer.normalizeAndSortWords("Mr Smith", 0));
        assertEquals(StringUtils.EMPTY, TextNormalizer.normalizeAndSortWords("Mr Smith", -5));
    }

}