package by.bivis.kbp.parser.parsers;

import by.bivis.kbp.parser.objects.Source;
import by.bivis.kbp.parser.objects.schedule.Schedule;
import by.bivis.kbp.parser.objects.schedule.ScheduleColumn;
import by.bivis.kbp.parser.objects.schedule.ScheduleSiteRow;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static by.bivis.kbp.parser.parsers.PageParser.getSchedulePage;
import static by.bivis.kbp.parser.parsers.SiteScheduleParser.getSiteApprovedRowList;
import static by.bivis.kbp.parser.parsers.SiteScheduleParser.getSiteSchedules;

public class ScheduleParser {

    /**
     * Flips the schedule from the site, creates ScheduleColumns from ScheduleSiteRows
     *
     * @param scheduleSiteRowList the site schedule row
     * @return schedule columns
     */
    private static List<ScheduleColumn> createScheduleColumnList(List<ScheduleSiteRow> scheduleSiteRowList) {
        List<ScheduleColumn> scheduleColumnList = new ArrayList<>();
        int scheduleTableWidth = scheduleSiteRowList.get(0).getCellList().size();
        for (int i = 0; i < scheduleTableWidth; i++) {
            ScheduleColumn column = new ScheduleColumn();
            for (ScheduleSiteRow scheduleSiteRow : scheduleSiteRowList) {
                column.getCellList().add(scheduleSiteRow.getCellList().get(i));
            }
            scheduleColumnList.add(column);
        }
        return scheduleColumnList;
    }

    /**
     * Joins two site schedules to one schedule column list
     *
     * @param siteSchedules two site schedules
     * @return joined schedule column list
     */
    private static List<ScheduleColumn> createUnitedScheduleColumnList(List<List<ScheduleSiteRow>> siteSchedules) {
        List<ScheduleColumn> unitedScheduleColumnList = new ArrayList<>();
        for (List<ScheduleSiteRow> scheduleSiteRows : siteSchedules) {
            unitedScheduleColumnList.addAll(createScheduleColumnList(scheduleSiteRows));
        }
        return unitedScheduleColumnList;
    }

    /**
     * Joins two site schedule approved rows into one list
     *
     * @param approvedLists two site schedule approved rows
     * @return joined approved list
     */
    private static List<Boolean> createUnitedColumnApprovedList(List<List<Boolean>> approvedLists) {
        List<Boolean> unitedColumnApprovedList = new ArrayList<>();
        for (List<Boolean> approvedList : approvedLists) {
            unitedColumnApprovedList.addAll(approvedList);
        }
        return unitedColumnApprovedList;
    }

    /**
     * Returns a schedule column list from site schedules with approved parameter
     *
     * @param unitedScheduleColumnList united schedule column list
     * @param unitedColumnApprovedList united column approved list
     * @return schedule column list
     */
    private static List<ScheduleColumn> createScheduleColumns(List<ScheduleColumn> unitedScheduleColumnList,
                                                              List<Boolean> unitedColumnApprovedList) {
        for (int i = 0; i < unitedScheduleColumnList.size(); i++) {
            ScheduleColumn column = unitedScheduleColumnList.get(i);
            column.setApproved(unitedColumnApprovedList.get(i));
        }
        return unitedScheduleColumnList;
    }

    /**
     * Returns a schedule by source from the site
     *
     * @param source the source
     * @return the schedule
     */
    public static Schedule getSchedule(Source source) {
        Document schedulePage = getSchedulePage(source);
        List<ScheduleColumn> unitedScheduleColumnList = createUnitedScheduleColumnList(getSiteSchedules(schedulePage));
        List<Boolean> unitedColumnApprovedList = createUnitedColumnApprovedList(getSiteApprovedRowList(schedulePage));
        List<ScheduleColumn> columns = createScheduleColumns(unitedScheduleColumnList, unitedColumnApprovedList);
        return new Schedule(new Date(), source, columns);
    }
}
