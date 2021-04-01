package parser;

import org.testng.annotations.Test;
import parser.objects.News;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class NewsParserTest extends BaseParserTest {
    @Test
    public void newsCountEqual15Test() {
        assertEquals(NewsParser.getNews().size(), 15);
    }

    @Test
    public void newsContentTest() {
        News news = NewsParser.getNews().get(2);
        assertEquals(news.getTitle(), "Конференция по результатам прохождения технологической практики по специальности «Правоведение»");
        assertEquals(news.getCaption(), "11 и 18 марта 2021 года цикловыми комиссиями юридических дисциплин была проведена конференция по итогам прохождения производственной (технологической) практики учащимися 3 курса специальности «Правоведение».");
        assertEquals(news.getArticleLink(), "https://kbp.by/itogi-proxozhdeniya-texnologicheskoj-praktiki/konferenciya-po-rezultatam-proxozhdeniya-texnologicheskoj-praktiki-po-specialnosti-pravovedenie/");
        assertEquals(news.getImgLink(), "https://kbp.by/wp-content/uploads/2021/03/praktika-Pravo.jpg");
    }

    @Test
    public void newsContainNoExtraTextTest() {
        List<News> newsList = NewsParser.getNews();
        for (News news : newsList) {
            assertFalse(news.getCaption().contains("(далее…)"));
        }
    }
}