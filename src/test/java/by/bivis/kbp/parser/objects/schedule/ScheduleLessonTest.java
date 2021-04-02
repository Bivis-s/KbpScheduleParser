package by.bivis.kbp.parser.objects.schedule;

import by.bivis.kbp.parser.enums.SourceType;
import by.bivis.kbp.parser.objects.Source;
import by.bivis.kbp.parser.parsers.BaseParserTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ScheduleLessonTest extends BaseParserTest {
    @Test
    public void getSourcesByTypeTest() {
        Source s1 = new Source("Aboba", "huh", SourceType.TEACHER);
        Source s2 = new Source("Aboba 2", "huh 2", SourceType.SUBJECT);
        Source s3 = new Source("Aboba 3", "huh 3", SourceType.TEACHER);
        Source s4 = new Source("Aboba 4", "huh 4", SourceType.AUDIENCE);
        List<Source> expectedSourceList = Arrays.asList(s1, s3);
        ScheduleLesson scheduleLesson = new ScheduleLesson();
        scheduleLesson.getSourceList().addAll(Arrays.asList(s1, s2, s3, s4));
        List<Source> actualSourceList = scheduleLesson.getSourcesByType(SourceType.TEACHER);
        assertThat(actualSourceList, equalTo(expectedSourceList));
    }
}