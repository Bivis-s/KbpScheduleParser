package by.bivis.kbp.parser.parsers;

import by.bivis.kbp.parser.context.Context;
import by.bivis.kbp.parser.objects.Source;
import by.bivis.kbp.parser.objects.schedule.Schedule;
import by.bivis.kbp.parser.parsers.values.pages.KbpTestPages;
import org.testng.annotations.BeforeTest;

import static by.bivis.kbp.parser.parsers.PageParser.getSourceListPage;

public class BaseParserTest {
    @BeforeTest(alwaysRun = true, enabled = true)
    public void setup() {
        //set test pages during testing
        Context.setPages(new KbpTestPages());
    }

    protected Source getTestSource() {
        return SourceParser.getAvailableSourceList(getSourceListPage()).get(118);
    }

    protected Schedule getTestSchedule() {
        return ScheduleParser.getSchedule(getTestSource());
    }
}
