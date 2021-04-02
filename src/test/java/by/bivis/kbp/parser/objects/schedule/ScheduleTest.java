package by.bivis.kbp.parser.objects.schedule;

import by.bivis.kbp.parser.parsers.BaseParserTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class ScheduleTest extends BaseParserTest {
    @Test
    public void getTodayScheduleReturnsNotNullTest() {
        assertNotNull(getTestSchedule().getTodaySchedule());
    }

    @Test
    public void getTodayAndTomorrowSchedulesSizeTest() {
        assertEquals(getTestSchedule().getTodayAndTomorrowSchedule().size(), 2);
    }

    @Test(enabled = false)
    public void printTodaySchedule() {
        System.out.println(getTestSchedule().getTodaySchedule());
    }

    @Test(enabled = false)
    public void printTodayAndTomorrowSchedules() {
        System.out.println(getTestSchedule().getTodayAndTomorrowSchedule());
    }
}