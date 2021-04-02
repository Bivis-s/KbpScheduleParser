package by.bivis.kbp.parser.parsers.values.pages;

public class KbpTestPages implements Pages {
    private static final String MAIN_PAGE_URL = "src/test/resources/main_page_for_tests.html";
    private static final String BASE_SCHEDULE_URL = "src/test/resources/schedule_page_for_tests.html";
    private static final String SOURCE_LIST_URL = "src/test/resources/source_list_page_for_tests.html";

    @Override
    public String getNewsPageUrl() {
        return MAIN_PAGE_URL;
    }

    @Override
    public String getSourceListPageUrl() {
        return SOURCE_LIST_URL;
    }

    @Override
    public String getSchedulePageUrl(String scheduleParam) {
        return BASE_SCHEDULE_URL;
    }
}
