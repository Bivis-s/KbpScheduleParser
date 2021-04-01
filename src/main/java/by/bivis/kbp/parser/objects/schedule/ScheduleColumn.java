package by.bivis.kbp.parser.objects.schedule;

import lombok.Value;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Value
public class ScheduleColumn {
    List<ScheduleCell> cellList = new ArrayList<>();
    Date parsingDate;
    boolean isApproved;

    public ScheduleColumn(Date parsingDate, boolean isApproved) {
        this.parsingDate = parsingDate;
        this.isApproved = isApproved;
    }
}
