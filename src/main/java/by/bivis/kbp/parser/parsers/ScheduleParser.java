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

    private static List<ScheduleColumn> createUnitedScheduleColumnList(List<List<ScheduleSiteRow>> siteSchedules) {
        List<ScheduleColumn> unitedScheduleColumnList = new ArrayList<>();
        for (List<ScheduleSiteRow> scheduleSiteRows : siteSchedules) {
            unitedScheduleColumnList.addAll(createScheduleColumnList(scheduleSiteRows));
        }
        return unitedScheduleColumnList;
    }

    private static List<Boolean> createUnitedColumnApprovedList(List<List<Boolean>> approvedLists) {
        List<Boolean> unitedColumnApprovedList = new ArrayList<>();
        for (List<Boolean> approvedList : approvedLists) {
            unitedColumnApprovedList.addAll(approvedList);
        }
        return unitedColumnApprovedList;
    }

    private static List<ScheduleColumn> createScheduleColumns(List<ScheduleColumn> unitedScheduleColumnList,
                                                              List<Boolean> unitedColumnApprovedList) {
        for (int i = 0; i < unitedScheduleColumnList.size(); i++) {
            ScheduleColumn column = unitedScheduleColumnList.get(i);
            column.setApproved(unitedColumnApprovedList.get(i));
        }
        return unitedScheduleColumnList;
    }

    public static Schedule getSchedule(Source source) {
        Document schedulePage = getSchedulePage(source);
        List<ScheduleColumn> unitedScheduleColumnList = createUnitedScheduleColumnList(getSiteSchedules(schedulePage));
        List<Boolean> unitedColumnApprovedList = createUnitedColumnApprovedList(getSiteApprovedRowList(schedulePage));
        List<ScheduleColumn> columns = createScheduleColumns(unitedScheduleColumnList, unitedColumnApprovedList);
        return new Schedule(new Date(), source, columns);
    }
}
