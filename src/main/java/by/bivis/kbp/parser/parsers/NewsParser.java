package by.bivis.kbp.parser.parsers;

import by.bivis.kbp.parser.Context;
import by.bivis.kbp.parser.objects.News;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static by.bivis.kbp.parser.parsers.PageParser.getPage;
import static by.bivis.kbp.parser.utils.JsoupUtils.*;
import static by.bivis.kbp.parser.utils.StringUtils.cutOutSubStrings;

public class NewsParser {
    private NewsParser() {
    }

    private static Elements getNewsCellElements() {
        Document document = getPage(Context.getPages().getMainPageUrl());
        return getInnerElements(document, Selector.NEWS_CELL_SELECTOR);
    }

    private static News createNews(Element newsElement) {
        News news = new News();
        Element title = getInnerElement(newsElement, Selector.NEWS_TITLE_SELECTOR);
        news.setTitle(title.text());
        news.setArticleLink(getHrefAttribute(title));
        String caption = getInnerElement(newsElement, Selector.NEWS_CAPTION_SELECTOR).text();
        news.setCaption(cutOutSubStrings(caption, "(далее…)").trim());
        news.setImgLink(getSrcAttribute(getInnerElement(newsElement, Selector.NEWS_IMG_SELECTOR)));
        return news;
    }

    private static List<News> createNewsList(Elements elements) {
        List<News> newsList = new ArrayList<>();
        for (Element element : elements) {
            newsList.add(createNews(element));
        }
        return newsList;
    }

    /**
     * Returns the news objects from the college site main page
     *
     * @return the news object list
     */
    public static List<News> getNews() {
        return createNewsList(getNewsCellElements());
    }

    private static class Selector {
        private static final String NEWS_CELL_SELECTOR = "[id~=site-main] [class~=pt-cv-page] div:only-child";
        private static final String NEWS_TITLE_SELECTOR = "[class*=title] a";
        private static final String NEWS_CAPTION_SELECTOR = "[class*=content]";
        private static final String NEWS_IMG_SELECTOR = "img";
    }
}
