package by.bivis.kbp.parser.parsers.values.pages;

public interface Pages {
    String getMainPageUrl();

    String getSourceListPageUrl();

    String getSchedulePageUrl(String scheduleParam);
}
