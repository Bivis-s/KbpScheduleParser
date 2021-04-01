package by.bivis.kbp.parser.parsers;

import by.bivis.kbp.parser.Context;
import by.bivis.kbp.parser.enums.SourceType;
import by.bivis.kbp.parser.errors.EmptyLessonContentError;
import by.bivis.kbp.parser.errors.NoEnumWithSuchValueError;
import by.bivis.kbp.parser.objects.Source;
import by.bivis.kbp.parser.objects.schedule.ScheduleCell;
import by.bivis.kbp.parser.objects.schedule.ScheduleLesson;
import by.bivis.kbp.parser.objects.schedule.ScheduleSiteRow;
import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static by.bivis.kbp.parser.parsers.PageParser.getPage;
import static by.bivis.kbp.parser.utils.JsoupUtils.*;

@Log4j2
public final class ScheduleParser {
    private ScheduleParser() {
    }

    /**
     * Returns left and right schedule tables
     *
     * @param sourceLink the source link, for example "?cat=subject&id=24"
     * @return the Elements, containing two schedule divs (left and right)
     */
    private static Elements getScheduleTables(String sourceLink) {
        Document document = getPage(Context.getPages().getSchedulePageUrl(sourceLink));
        return getInnerElements(document, Selector.WEEK_SELECTOR);
    }

    private static Source createSourceFromLessonContent(Element lessonContent) {
        String selector = "a";
        Element linkText = getInnerElement(lessonContent, selector);
        String elementText = linkText.text();
        if (elementText != null && !elementText.equals("")) {
            try {
                Source source = new Source();
                source.setValue(elementText);
                source.setLinkParameter(getHrefAttribute(linkText));
                source.setType(SourceType.getEnumByEngValue(getClassName(lessonContent)));
                return source;
            } catch (NoEnumWithSuchValueError ignored) {
            }
        }
        throw new EmptyLessonContentError("Element '" + lessonContent.html() + "' has no text");
    }

    private static ScheduleLesson createScheduleLesson(Element lesson) {
        String lessonContentSelector = "[class~=subject], [class~=teacher], [class~=group], [class~=place]";
        Elements lessonContent = getInnerElements(lesson, lessonContentSelector);
        ScheduleLesson scheduleLesson = new ScheduleLesson();
        for (Element element : lessonContent) {
            try {
                scheduleLesson.getSourceList().add(createSourceFromLessonContent(element));
            } catch (EmptyLessonContentError ignored) {
            }
        }
        return scheduleLesson;
    }

    private static ScheduleCell createScheduleCell(Element cell) {
        String lessonSelector = "[class~=pair]:not([class~=removed]):not([class~=empty-pair])";
        Elements lessons = getInnerElements(cell, lessonSelector);
        ScheduleCell scheduleCell = new ScheduleCell();
        for (Element lesson : lessons) {
            scheduleCell.getLessonList().add(createScheduleLesson(lesson));
        }
        return scheduleCell;
    }

    private static ScheduleSiteRow createScheduleSiteRow(Element row) {
        String cellSelector = "> *:not([class='number'])";
        Elements cells = getInnerElements(row, cellSelector);
        ScheduleSiteRow scheduleSiteRow = new ScheduleSiteRow();
        for (Element cell : cells) {
            scheduleSiteRow.getCellList().add(createScheduleCell(cell));
        }
        return scheduleSiteRow;
    }

    private static List<ScheduleSiteRow> createScheduleSiteRowList(Element schedule) {
        String rowSelector = "[class~=zamena] ~ *";
        Elements rows = getInnerElements(schedule, rowSelector);
        List<ScheduleSiteRow> scheduleSiteRowList = new ArrayList<>();
        for (Element row : rows) {
            scheduleSiteRowList.add(createScheduleSiteRow(row));
        }
        return scheduleSiteRowList;
    }


    /**
     * Returns two lists of ScheduleSiteRow: for left (0) and right (1) schedules
     *
     * @return the site schedules list
     */
    public static List<List<ScheduleSiteRow>> getSiteSchedules(Source source) {
        List<List<ScheduleSiteRow>> siteSchedules = new ArrayList<>();
        for (Element schedule : getScheduleTables(source.getLinkParameter())) {
            siteSchedules.add(createScheduleSiteRowList(schedule));
        }
        return siteSchedules;
    }

    private static class Selector {
        private static final String WEEK_SELECTOR = "[id*=week]";
    }
}
