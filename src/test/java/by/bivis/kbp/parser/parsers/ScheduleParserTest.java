package by.bivis.kbp.parser.parsers;

import by.bivis.kbp.parser.enums.SourceType;
import by.bivis.kbp.parser.objects.Source;
import by.bivis.kbp.parser.objects.schedule.Schedule;
import by.bivis.kbp.parser.objects.schedule.ScheduleCell;
import by.bivis.kbp.parser.objects.schedule.ScheduleColumn;
import org.testng.annotations.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ScheduleParserTest extends BaseParserTest {
    private Schedule getTestSchedule() {
        return ScheduleParser.getSchedule(getTestSource());
    }

    @Test
    public void scheduleSizeTest() {
        int expectedScheduleSize = 12;
        int actualScheduleSize = getTestSchedule().getColumns().size();
        assertEquals(actualScheduleSize, expectedScheduleSize);
    }

    @Test
    public void scheduleContentTest() {
        Source expectedSource = new Source("ИнформТехнол", "?cat=subject&id=14", SourceType.SUBJECT);
        Schedule schedule = getTestSchedule();
        List<Source> actualSourceList = schedule.getColumns().get(5).getCellList().get(3).getLessonList().get(0).getSourceList();
        assertThat(actualSourceList, hasItem(expectedSource));
    }

    @Test
    public void lastCellOfFirstWeekIsEmptyTest() {
        boolean actualIsEmptyCondition = getTestSchedule().getColumns().get(5).getCellList().get(6).isEmpty();
        assertTrue(actualIsEmptyCondition);
    }

    @Test
    public void lastCellOfSecondWeekIsEmptyTest() {
        boolean actualIsEmptyCondition = getTestSchedule().getColumns().get(11).getCellList().get(6).isEmpty();
        assertTrue(actualIsEmptyCondition);
    }

    @Test(enabled = false)
    public void printSchedule() {
        Schedule schedule = getTestSchedule();
        System.out.println("Source: " + schedule.getSource());
        System.out.println("Parsing date: " + schedule.getParsingDate());
        for (ScheduleColumn scheduleColumn : schedule.getColumns()) {
            System.out.println("Is approved: " + scheduleColumn.isApproved());
            for (ScheduleCell cell : scheduleColumn.getCellList()) {
                System.out.println(cell.getLessonList());
            }
            System.out.println();
        }
    }
}