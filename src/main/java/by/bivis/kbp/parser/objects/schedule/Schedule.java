package by.bivis.kbp.parser.objects.schedule;

import by.bivis.kbp.parser.objects.Source;

import lombok.Value;

import java.util.Date;
import java.util.List;

@Value
public class Schedule {
    Date parsingDate;
    Source source;
    List<ScheduleColumn> columns;

    //TODO Implement picking column(s) by day/date
}
