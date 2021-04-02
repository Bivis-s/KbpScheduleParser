package by.bivis.kbp.parser.objects.schedule;

import by.bivis.kbp.parser.objects.Source;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@NoArgsConstructor
public class ScheduleLesson {
    List<Source> sourceList = new ArrayList<>();

    //TODO IMPLEMENT getTeacherList, getGroup etc.
}
