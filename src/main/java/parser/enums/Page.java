package parser.enums;

public enum Page implements EnumValue {
    MAIN(Urls.MAIN_PAGE_URL),
    SOURCE_LIST(Urls.SOURCE_LIST_URL),
    SCHEDULE(Urls.RAW_SCHEDULE_URL);

    private final String value;

    Page(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    private static class Urls {
        private static final String MAIN_PAGE_URL = "https://kbp.by/";
        private static final String BASE_SCHEDULE_URL = MAIN_PAGE_URL + "rasp/timetable/view_beta_kbp/";
        private static final String SOURCE_LIST_URL = BASE_SCHEDULE_URL + "?q=";
        private static final String RAW_SCHEDULE_URL = BASE_SCHEDULE_URL + "?cat=%s&id=%s";
    }
}
