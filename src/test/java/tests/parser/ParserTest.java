package tests.parser;

import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Document;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import parser.NewsParser;
import parser.PageParser;
import parser.SourceParser;
import parser.enums.Page;
import parser.objects.News;
import parser.objects.Source;
import tests.BaseTest;

import java.util.List;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static parser.utils.JsoupUtils.getInnerElement;

@Log4j2
public class ParserTest extends BaseTest {

    // Waits after each method for not to overload the server
    @AfterMethod
    public void teardown() throws InterruptedException {
        Thread.sleep(500);
    }

    @Test
    public void xpathUtilsGetNewsTitleOnMainPageTest() {
        String cssSelector = "[id~=site-main] [class~=entry-header] h1";
        Document document = PageParser.getPage(Page.MAIN);
        String newsTitleText = getInnerElement(document, cssSelector).text();
        assertTrue(newsTitleText.contains("Колледж бизнеса и права"));
    }

    @Test
    public void newsParserGetNewsTest() {
        List<News> newsList = NewsParser.getNews();
        log.info(newsList.toString());
        assertTrue(newsList.size() > 0);
    }

    @Test
    public void newsParserNewsFieldsTest() {
        News news = NewsParser.getNews().get(0);
        assertNotNull(news.getTitle());
        assertNotNull(news.getImgLink());
        assertNotNull(news.getCaption());
        assertNotNull(news.getArticleLink());
    }

    @Test
    public void sourceParserGetAllSourcesTest() {
        List<Source> sourceList = SourceParser.getSourceList();
        log.info(sourceList.toString());
        assertTrue(sourceList.size() > 0);
    }

    @Test
    public void sourceParserGetGroupSourcesTest() {
        // TODO IMPLEMENT
    }
}
