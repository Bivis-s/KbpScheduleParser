package by.bivis.kbp.parser.objects.schedule;

import by.bivis.kbp.parser.objects.Source;
import lombok.Value;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static by.bivis.kbp.parser.utils.DateUtils.getDayNumber;

@Value
public class Schedule {
    Date parsingDate;
    Source source;
    List<ScheduleColumn> columns;

    /**
     * Returns today schedule. On Sunday returns Monday's schedule
     *
     * @return the today schedule
     */
    public ScheduleColumn getTodaySchedule() {
        return columns.get(getDayNumber(LocalDate.now()) - 1);
    }

    /**
     * Returns today and tomorrow schedule. On Sunday returns Monday's and Tuesday's schedule
     *
     * @return the today and tomorrow schedule list
     */
    public List<ScheduleColumn> getTodayAndTomorrowSchedule() {
        return Arrays.asList(getTodaySchedule(), columns.get(getDayNumber(LocalDate.now())));
    }
}
