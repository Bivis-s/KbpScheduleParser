package by.bivis.kbp.parser.parsers;

import by.bivis.kbp.parser.Context;
import by.bivis.kbp.parser.objects.Source;
import by.bivis.kbp.parser.parsers.values.pages.KbpTestPages;
import by.bivis.kbp.parser.parsers.values.pages.Pages;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import static by.bivis.kbp.parser.parsers.PageParser.getSourceListPage;

public class BaseParserTest {
    private Pages contextPages;

    @BeforeTest(alwaysRun = true, enabled = true)
    public void setup() {
        //set test pages during testing
        contextPages = Context.getPages();
        Context.setPages(new KbpTestPages());
    }

    @AfterTest(alwaysRun = true, enabled = true)
    public void teardown() {
        // set past pages that were before testing
        Context.setPages(contextPages);
    }

    protected Source getTestSource() {
        return SourceParser.getAvailableSourceList(getSourceListPage()).get(118);
    }
}
