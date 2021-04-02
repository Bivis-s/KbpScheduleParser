package by.bivis.kbp.parser.parsers;

import by.bivis.kbp.parser.objects.News;
import by.bivis.kbp.parser.objects.Source;
import by.bivis.kbp.parser.objects.schedule.Schedule;

import java.util.List;

public final class Parser {
    private Parser() {
    }

    /**
     * Returns news from the site
     *
     * @return the news
     */
    public static List<News> getNews() {
        return NewsParser.getNews(PageParser.getNewsPage());
    }

    /**
     * Returns an available source list from the site
     *
     * @return the available source list
     */
    public static List<Source> getAvailableSourceList() {
        return SourceParser.getAvailableSourceList(PageParser.getSourceListPage());
    }

    /**
     * Returns a schedule by source from the site
     *
     * @param source the source
     * @return the schedule
     */
    public static Schedule getSchedule(Source source) {
        return ScheduleParser.getSchedule(source);
    }
}
