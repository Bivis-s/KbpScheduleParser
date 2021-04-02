package by.bivis.kbp.parser.parsers.values.pages;

public class KbpPages implements Pages {
    private static final String MAIN_PAGE_URL = "https://kbp.by/";
    private static final String BASE_SCHEDULE_URL = MAIN_PAGE_URL + "rasp/timetable/view_beta_kbp/";
    private static final String SOURCE_LIST_URL = BASE_SCHEDULE_URL + "?q=";

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
        return BASE_SCHEDULE_URL + scheduleParam;
    }
}
