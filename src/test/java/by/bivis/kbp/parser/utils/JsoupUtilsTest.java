package by.bivis.kbp.parser.utils;

import by.bivis.kbp.parser.context.Context;
import by.bivis.kbp.parser.parsers.BaseParserTest;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOError;
import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class JsoupUtilsTest extends BaseParserTest {
    private Document getDocument(String pageUrlOrHtmlPath) {
        try {
            try {
                return Jsoup
                        .connect(pageUrlOrHtmlPath)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36")
                        .get();
            } catch (IOException ioException) {
                throw new IOError(ioException);
            }
        } catch (IllegalArgumentException e) {
            try {
                return Jsoup
                        .parse(new File(pageUrlOrHtmlPath), "UTF-8");
            } catch (IOException ioException) {
                throw new IOError(ioException);
            }
        }
    }

    @Test
    public void getInnerElementsTest() {
        Document document = getDocument(Context.getPages().getSourceListPageUrl());
        Elements elements = JsoupUtils.getInnerElements(document,
                "[class~=block_back] [class~=text_h1]:first-of-type ~ *");
        assertEquals(elements.size(), 364);
    }

    @Test
    public void getInnerElementTest() {
        String cssSelector = "[id~=site-main] [class~=entry-header] h1";
        Document document = getDocument(Context.getPages().getNewsPageUrl());
        String newsTitleText = JsoupUtils.getInnerElement(document, cssSelector).text();
        assertTrue(newsTitleText.contains("Колледж бизнеса и права"));
    }

    @Test
    public void getHrefAttributeTest() {
        Document document = getDocument(Context.getPages().getSourceListPageUrl());
        Element element = JsoupUtils.getInnerElement(document, "[href='?cat=place&id=40']");
        String href = JsoupUtils.getHrefAttribute(element);
        assertEquals(href, "?cat=place&id=40");
    }

    @Test
    public void getSrcAttributeTest() {
        Document document = getDocument(Context.getPages().getNewsPageUrl());
        Element element = JsoupUtils.getInnerElement(document,
                "[src='https://kbp.by/wp-content/uploads/2021/03/praktika-BD.jpg']");
        String src = JsoupUtils.getSrcAttribute(element);
        assertEquals(src, "https://kbp.by/wp-content/uploads/2021/03/praktika-BD.jpg");

    }
}