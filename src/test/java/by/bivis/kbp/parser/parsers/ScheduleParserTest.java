package by.bivis.kbp.parser.parsers;

import by.bivis.kbp.parser.enums.SourceType;
import by.bivis.kbp.parser.objects.Source;
import by.bivis.kbp.parser.objects.schedule.ScheduleCell;
import by.bivis.kbp.parser.objects.schedule.ScheduleLesson;
import by.bivis.kbp.parser.objects.schedule.ScheduleSiteRow;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


@Log4j2
public class ScheduleParserTest extends BaseParserTest {
    private List<List<ScheduleSiteRow>> getSiteSchedules() {
        Source source = SourceParser.getAvailableSourceList().get(118);
        return ScheduleParser.getSiteSchedules(source);
    }

    @Test
    public void siteSchedulesSizeTest() {
        List<List<ScheduleSiteRow>> siteSchedules = getSiteSchedules();
        assertEquals(siteSchedules.size(), 2);
    }

    @Test
    public void leftWeekScheduleRowSizeTest() {
        List<ScheduleSiteRow> leftWeekSchedule = getSiteSchedules().get(0);
        assertEquals(leftWeekSchedule.size(), 7);
    }

    @Test
    public void emptyCellTest() {
        List<ScheduleSiteRow> schedule = getSiteSchedules().get(0);
        ScheduleCell cell = schedule.get(0).getCellList().get(0);
        assertTrue(cell.isEmpty());
    }

    @Test
    public void scheduleCellSizeTest() {
        List<ScheduleSiteRow> schedule = getSiteSchedules().get(0);
        ScheduleCell cell = schedule.get(2).getCellList().get(0);
        List<ScheduleLesson> lessons = cell.getLessonList();
        assertEquals(lessons.size(), 2);
    }

    @Test
    public void lessonContentTest(){
        Source s0 = new Source("ИнЯзДел", "?cat=subject&id=65", SourceType.SUBJECT, null);
        Source s1 = new Source("Бондаренко", "?cat=teacher&id=69", SourceType.TEACHER, null);
        Source s2 = new Source("Янкун Н.Ю.", "?cat=teacher&id=70", SourceType.TEACHER, null);
        Source s3 = new Source("Д-013", "?cat=group&id=48", SourceType.GROUP, null);
        Source s4 = new Source("228", "?cat=place&id=57", SourceType.AUDIENCE, null);
        List<ScheduleSiteRow> schedule = getSiteSchedules().get(0);
        ScheduleCell cell = schedule.get(2).getCellList().get(2);
        ScheduleLesson lesson = cell.getLessonList().get(0);
        assertThat(lesson.getSourceList(), hasItems(s0, s1, s2, s3, s4));
    }
}