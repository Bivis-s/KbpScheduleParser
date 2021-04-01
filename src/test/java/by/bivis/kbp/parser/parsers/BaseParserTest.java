package by.bivis.kbp.parser.parsers;

import by.bivis.kbp.parser.Context;
import by.bivis.kbp.parser.parsers.values.pages.KbpTestPages;
import by.bivis.kbp.parser.parsers.values.pages.Pages;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseParserTest {
    private Pages contextPages;

    @BeforeTest(alwaysRun = true, enabled = false)
    public void setup() {
        //set test pages during testing
        contextPages = Context.getPages();
        Context.setPages(new KbpTestPages());
    }

    @AfterTest(alwaysRun = true, enabled = false)
    public void teardown() {
        // set past pages that were before testing
        Context.setPages(contextPages);
    }
}
