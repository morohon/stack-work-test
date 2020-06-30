package ru.stack.test.verevkin.task1_2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.stack.test.verevkin.task1.Matcher;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kirill Verevkin
 */
class MatcherTest {

    @Test
    void match() {

        assertFalse(ru.stack.test.verevkin.task1.Matcher.match("xy", "a"));
        assertFalse(ru.stack.test.verevkin.task1.Matcher.match("x", "d"));
        assertFalse(ru.stack.test.verevkin.task1.Matcher.match("0", "a"));
        assertFalse(ru.stack.test.verevkin.task1.Matcher.match("*", " "));
        assertFalse(ru.stack.test.verevkin.task1.Matcher.match(" ", "a"));

        assertTrue(ru.stack.test.verevkin.task1.Matcher.match("01 xy", "dd aa"));
        assertTrue(ru.stack.test.verevkin.task1.Matcher.match("1x", "**"));

        assertThrows(IllegalArgumentException.class, () -> Matcher.match("x", "w"));

    }
}