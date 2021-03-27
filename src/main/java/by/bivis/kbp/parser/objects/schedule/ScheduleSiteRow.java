package by.bivis.kbp.parser.objects.schedule;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class ScheduleSiteRow {
    List<ScheduleCell> cellList = new ArrayList<>();
}
