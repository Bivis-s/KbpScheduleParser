package parser;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import parser.values.pages.KbpTestPages;
import parser.values.pages.Pages;

public class BaseParserTest {
    private Pages contextPages;

    @BeforeTest(alwaysRun = true)
    public void setup() {
        //set test pages during testing
        contextPages = Context.getPages();
        Context.setPages(new KbpTestPages());
    }

    @AfterTest(alwaysRun = true)
    public void teardown() {
        // set past pages that were before testing
        Context.setPages(contextPages);
    }
}
