package ru.stack.test.verevkin.task1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kirill Verevkin
 */
class MatcherTest {

    @Test
    void matcher() {

        Assertions.assertFalse(Matcher.match("xy", "a"));
        assertFalse(Matcher.match("x", "d"));
        assertFalse(Matcher.match("0", "a"));
        assertFalse(Matcher.match("*", " "));
        assertFalse(Matcher.match(" ", "a"));

        assertTrue(Matcher.match("01 xy", "dd aa"));
        assertTrue(Matcher.match("1x", "**"));

        assertThrows(IllegalArgumentException.class, () -> Matcher.match("x", "w"));

    }
}