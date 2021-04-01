package by.bivis.kbp.parser.objects.schedule;

import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class ScheduleCell {
    List<ScheduleLesson> lessonList = new ArrayList<>();

    public boolean isEmpty() {
        return lessonList.isEmpty();
    }
}
