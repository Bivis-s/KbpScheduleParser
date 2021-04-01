package by.bivis.kbp.parser.objects;

import by.bivis.kbp.parser.enums.SourceType;
import by.bivis.kbp.parser.objects.schedule.ScheduleColumn;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Source {
    private String value;
    private String link;
    private SourceType type;
    private List<ScheduleColumn> scheduleColumns;
}
