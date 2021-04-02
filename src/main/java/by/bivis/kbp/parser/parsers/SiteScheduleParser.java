package by.bivis.kbp.parser.parsers;

import by.bivis.kbp.parser.enums.SourceType;
import by.bivis.kbp.parser.errors.EmptyLessonContentError;
import by.bivis.kbp.parser.errors.NoEnumWithSuchValueError;
import by.bivis.kbp.parser.objects.Source;
import by.bivis.kbp.parser.objects.schedule.ScheduleCell;
import by.bivis.kbp.parser.objects.schedule.ScheduleLesson;
import by.bivis.kbp.parser.objects.schedule.ScheduleSiteRow;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static by.bivis.kbp.parser.utils.JsoupUtils.*;
import static by.bivis.kbp.parser.utils.StringUtils.isEmptyIgnoringSpaces;

class SiteScheduleParser {
    private SiteScheduleParser() {
    }

    /**
     * Returns left and right schedule tables
     *
     * @param schedulePage the schedule page Document
     * @return the Elements, containing two schedule divs (left and right)
     */
    private static Elements getScheduleTables(Document schedulePage) {
        return getInnerElements(schedulePage, Selector.WEEK_SELECTOR);
    }

    private static Source createSourceFromLessonContent(Element lessonContent) {
        Element linkText = getInnerElement(lessonContent, Selector.LINK_TEXT_SELECTOR);
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
        Elements lessonContent = getInnerElements(lesson, Selector.SCHEDULE_LESSON_CONTENT_SELECTOR);
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
        Elements lessons = getInnerElements(cell, Selector.SCHEDULE_LESSON_SELECTOR);
        ScheduleCell scheduleCell = new ScheduleCell();
        for (Element lesson : lessons) {
            scheduleCell.getLessonList().add(createScheduleLesson(lesson));
        }
        return scheduleCell;
    }

    private static ScheduleSiteRow createScheduleSiteRow(Element row) {
        Elements cells = getInnerElements(row, Selector.SCHEDULE_CELL_SELECTOR);
        ScheduleSiteRow scheduleSiteRow = new ScheduleSiteRow();
        for (Element cell : cells) {
            scheduleSiteRow.getCellList().add(createScheduleCell(cell));
        }
        return scheduleSiteRow;
    }

    private static List<ScheduleSiteRow> createScheduleSiteRowList(Element schedule) {
        Elements rows = getInnerElements(schedule, Selector.SCHEDULE_ROW_SELECTOR);
        List<ScheduleSiteRow> scheduleSiteRowList = new ArrayList<>();
        for (Element row : rows) {
            scheduleSiteRowList.add(createScheduleSiteRow(row));
        }
        return scheduleSiteRowList;
    }


    /**
     * Returns two lists of ScheduleSiteRow: for left (0) and right (1) schedules
     *
     * @param schedulePage the schedule page Document
     * @return the site schedules list
     */
    public static List<List<ScheduleSiteRow>> getSiteSchedules(Document schedulePage) {
        List<List<ScheduleSiteRow>> siteSchedules = new ArrayList<>();
        for (Element schedule : getScheduleTables(schedulePage)) {
            siteSchedules.add(createScheduleSiteRowList(schedule));
        }
        return siteSchedules;
    }

    private static boolean isApproved(Element approvedCell) {
        return !isEmptyIgnoringSpaces(approvedCell.text());
    }

    private static List<Boolean> createApprovedListForSchedule(Element schedule) {
        List<Boolean> approvedList = new ArrayList<>();
        for (Element element : getInnerElements(schedule, Selector.APPROVED_CELL_SELECTOR)) {
            approvedList.add(isApproved(element));
        }
        return approvedList;
    }

    /**
     * Returns an approved row list.
     * If the approved cell is not empty, true (it means that the schedule column is approved and will not be changed),
     * Else, false
     *
     * @param schedulePage the schedule page Document
     * @return the site approved row list
     */
    public static List<List<Boolean>> getSiteApprovedRowList(Document schedulePage) {
        List<List<Boolean>> siteApprovedRowList = new ArrayList<>();
        for (Element schedule : getScheduleTables(schedulePage)) {
            siteApprovedRowList.add(createApprovedListForSchedule(schedule));
        }
        return siteApprovedRowList;
    }

    private static class Selector {
        private static final String WEEK_SELECTOR = "[id*=week]";
        private static final String APPROVED_CELL_SELECTOR = ".zamena th:nth-child(1) ~ th:not(th:last-child)";
        private static final String SCHEDULE_ROW_SELECTOR = "[class~=zamena] ~ *";
        private static final String SCHEDULE_CELL_SELECTOR = "> *:not([class='number'])";
        private static final String SCHEDULE_LESSON_SELECTOR =
                "[class~=pair]:not([class~=removed]):not([class~=empty-pair])";
        private static final String SCHEDULE_LESSON_CONTENT_SELECTOR =
                "[class~=subject], [class~=teacher], [class~=group], [class~=place]";
        private static final String LINK_TEXT_SELECTOR = "a";
    }
}
