package parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parser.enums.Page;
import parser.objects.News;
import us.codecraft.xsoup.Xsoup;

import java.util.ArrayList;
import java.util.List;

import static parser.utils.StringUtils.cutOutSubStrings;
import static parser.utils.XpathUtils.*;

public class NewsParser {
    private static Elements getNewsCellElements() {
        Document document = PageParser.getPage(Page.MAIN);
        return Xsoup.compile(Xpath.NEWS_CELL_XPATH).evaluate(document).getElements();
    }

    private static News createNews(Element newsElement) {
        News news = new News();
        Element title = getInnerElement(newsElement, Xpath.NEWS_TITLE_XPATH);
        news.setTitle(title.text());
        news.setArticleLink(getHrefAttribute(title));
        String caption = getInnerElement(newsElement, Xpath.NEWS_CAPTION_XPATH).text();
        news.setCaption(cutOutSubStrings(caption, "(далее…)").trim());
        news.setImgLink(getSrcAttribute(getInnerElement(newsElement, Xpath.NEWS_IMG_XPATH)));
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
     * @return the news objects
     */
    public static List<News> getNews() {
        return createNewsList(getNewsCellElements());
    }

    private static class Xpath {
        private static final String NEWS_CELL_XPATH =
                "//*[contains(@class,'entry-content')]//*[contains(@class,'content-item')]";
        private static final String NEWS_TITLE_XPATH = "//*[contains(@class,'title')]/a";
        private static final String NEWS_CAPTION_XPATH = "//*[contains(@class,'content')]";
        private static final String NEWS_IMG_XPATH = "//img";
    }
}
