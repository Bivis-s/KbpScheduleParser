package by.bivis.kbp.parser.objects.schedule;

import by.bivis.kbp.parser.enums.SourceType;
import by.bivis.kbp.parser.objects.Source;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@NoArgsConstructor
public class ScheduleLesson {
    List<Source> sourceList = new ArrayList<>();

    public List<Source> getSourcesByType(SourceType type) {
        List<Source> sourcesByType = new ArrayList<>();
        for (Source source : sourceList) {
            if (source.getType() == type) {
                sourcesByType.add(source);
            }
        }
        return sourcesByType;
    }
}
