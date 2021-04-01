package parser.utils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;
import parser.BaseParserTest;
import parser.Context;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static parser.PageParser.getPage;

public class JsoupUtilsTest extends BaseParserTest {
    @Test
    public void getInnerElementsTest() {
        Document document = getPage(Context.getPages().getSourceListPageUrl());
        Elements elements = JsoupUtils.getInnerElements(document,
                "[class~=block_back] [class~=text_h1]:first-of-type ~ *");
        assertEquals(elements.size(), 364);
    }

    @Test
    public void getInnerElementTest() {
        String cssSelector = "[id~=site-main] [class~=entry-header] h1";
        Document document = getPage(Context.getPages().getMainPageUrl());
        String newsTitleText = JsoupUtils.getInnerElement(document, cssSelector).text();
        assertTrue(newsTitleText.contains("Колледж бизнеса и права"));
    }

    @Test
    public void getHrefAttributeTest() {
        Document document = getPage(Context.getPages().getSourceListPageUrl());
        Element element = JsoupUtils.getInnerElement(document, "[href='?cat=place&id=40']");
        String href = JsoupUtils.getHrefAttribute(element);
        assertEquals(href, "?cat=place&id=40");
    }

    @Test
    public void getSrcAttributeTest() {
        Document document = getPage(Context.getPages().getMainPageUrl());
        Element element = JsoupUtils.getInnerElement(document,
                "[src='https://kbp.by/wp-content/uploads/2021/03/praktika-BD.jpg']");
        String src = JsoupUtils.getSrcAttribute(element);
        assertEquals(src, "https://kbp.by/wp-content/uploads/2021/03/praktika-BD.jpg");

    }
}