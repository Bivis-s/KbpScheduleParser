package by.bivis.kbp.parser.utils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class StringUtils {
    private StringUtils() {
    }


    /**
     * Cut out all sub strings from main string.
     * For example, if mainString = "the string" and subString = "the " then "string" will return
     *
     * @param mainString the main string
     * @param subString  the sub string
     * @return the string with no sub strings
     */
    public static String cutOutSubStrings(String mainString, String subString) {
        log.trace("Cut out all substrings '" + subString + "' from string '" + mainString + "'");
        while (mainString.contains(subString)) {
            int indexOfSubString = mainString.indexOf(subString);
            int lastIndexOfSubString = indexOfSubString + subString.length();
            String firstPartOfString = mainString.substring(0, indexOfSubString);
            String secondPartOfString = mainString.substring(lastIndexOfSubString);
            mainString = firstPartOfString + secondPartOfString;
            log.trace("Result of cutting out '" + mainString + "'");
        }
        return mainString;
    }

    public static boolean isEmptyIgnoringSpaces(String string) {
        String trimmedString = string.trim();
        return trimmedString.equals("");
    }
}
