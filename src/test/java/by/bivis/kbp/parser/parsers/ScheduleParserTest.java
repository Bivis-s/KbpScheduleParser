package by.bivis.kbp.parser.parsers;

import by.bivis.kbp.parser.objects.Source;
import by.bivis.kbp.parser.objects.schedule.ScheduleSiteRow;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

import java.util.List;

import static by.bivis.kbp.parser.parsers.ScheduleParser.getSiteSchedules;
import static org.testng.Assert.assertTrue;

@Log4j2
public class ScheduleParserTest {
    @Test
    public void getSiteSchedulesTest() {
        Source source = SourceParser.getAvailableSourceList().get(0);
        List<List<ScheduleSiteRow>> siteSchedules = getSiteSchedules(source);
        log.info(siteSchedules);
        assertTrue(siteSchedules.size() > 0);
    }
}