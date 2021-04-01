package tests.parser;

import by.bivis.kbp.parser.utils.StringUtils;
import org.testng.annotations.Test;
import parser.BaseParserTest;

import static org.testng.Assert.assertEquals;

public class StringUtilsTest extends BaseParserTest {
    @Test
    public void cutOutSubStringInTheStartTest() {
        String actual = StringUtils.cutOutSubStrings("Some strange text", "Some ");
        String expected = "strange text";
        assertEquals(actual, expected);
    }

    @Test
    public void cutOutSubStringInTheMiddleTest() {
        String actual = StringUtils.cutOutSubStrings("Some strange text", " strange");
        String expected = "Some text";
        assertEquals(actual, expected);
    }

    @Test
    public void cutOutSubStringInTheEndTest() {
        String actual = StringUtils.cutOutSubStrings("Some strange text", " text");
        String expected = "Some strange";
        assertEquals(actual, expected);
    }

    @Test
    public void cutOutSubStringsInTheStartTest() {
        String actual = StringUtils.cutOutSubStrings("Some Some strange strange text text", "Some ");
        String expected = "strange strange text text";
        assertEquals(actual, expected);
    }

    @Test
    public void cutOutSubStringsInTheMiddleTest() {
        String actual = StringUtils.cutOutSubStrings("Some Some strange strange text text", " strange");
        String expected = "Some Some text text";
        assertEquals(actual, expected);
    }

    @Test
    public void cutOutSubStringsInTheEndTest() {
        String actual = StringUtils.cutOutSubStrings("Some Some strange strange text text", " text");
        String expected = "Some Some strange strange";
        assertEquals(actual, expected);
    }
}