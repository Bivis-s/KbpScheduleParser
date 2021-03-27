package by.bivis.kbp.parser.enums;

public enum Page implements EnumValue {
    MAIN(Urls.MAIN_PAGE_URL),
    SOURCE_LIST(Urls.SOURCE_LIST_URL),
    SCHEDULE(Urls.BASE_SCHEDULE_URL);

    private final String value;

    Page(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    /**
     * Returns enum value + {@param stringForConcatenation}
     * <p>
     * For example:
     * When
     * SCHEDULE.getValue() returns "https://kbp.by/rasp/timetable/view_beta_kbp/"
     * Then
     * SCHEDULE.getValue("?cat=place&id=40") will return "https://kbp.by/rasp/timetable/view_beta_kbp/?cat=place&id=40"
     *
     * @param stringForConcatenation the string for concatenation
     * @return the value concatenated with {@param stringForConcatenation}
     */
    public String getValue(String stringForConcatenation) {
        return value + stringForConcatenation;
    }

    private static class Urls {
        private static final String MAIN_PAGE_URL = "https://kbp.by/";
        private static final String BASE_SCHEDULE_URL = MAIN_PAGE_URL + "rasp/timetable/view_beta_kbp/";
        private static final String SOURCE_LIST_URL = BASE_SCHEDULE_URL + "?q=";
    }
}
