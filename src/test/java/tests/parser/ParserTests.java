package tests.parser;

import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Document;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import parser.PageParser;
import parser.enums.Page;
import tests.BaseTest;
import us.codecraft.xsoup.Xsoup;

import static org.testng.Assert.assertTrue;

@Log4j2
public class ParserTests extends BaseTest {

    // Waits after each method for not to overload the server
    @AfterMethod
    public void teardown() throws InterruptedException {
        Thread.sleep(500);
    }

    @Test
    public void getNewsTitleOnMainPageTest() {
        String selector = "//*[contains(@id,'site-main')]//*[contains(@class,'entry-header')]//h1";
        Document document = PageParser.getPage(Page.MAIN);
        String newsTitleText = Xsoup
                .compile(selector)
                .evaluate(document)
                .getElements()
                .get(0)
                .text();
        assertTrue(newsTitleText.contains("Колледж бизнеса и права"));
    }
}
