package by.bivis.kbp.parser.objects;

import by.bivis.kbp.parser.enums.SourceType;
import by.bivis.kbp.parser.objects.schedule.ScheduleColumn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Source {
    private String value;
    private String linkParameter;
    private SourceType type;
    private List<ScheduleColumn> scheduleColumns;
}
