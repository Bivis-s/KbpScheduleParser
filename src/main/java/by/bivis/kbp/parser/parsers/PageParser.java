package by.bivis.kbp.parser.parsers;

import by.bivis.kbp.parser.context.Context;
import by.bivis.kbp.parser.objects.Source;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOError;
import java.io.IOException;

@Log4j2
class PageParser {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36";

    private PageParser() {
    }

    private static Document getPageFromUrl(String url) {
        try {
            return Jsoup
                    .connect(url)
                    .userAgent(USER_AGENT)
                    .get();
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
            throw new IOError(ioException);
        }
    }

    private static Document getPageFromPath(String path) {
        try {
            return Jsoup
                    .parse(new File(path), "UTF-8");
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
            throw new IOError(ioException);
        }
    }

    /**
     * Returns page document by url or html file path
     *
     * @param pageUrlOrHtmlPath url or html file path
     * @return page document
     */
    protected static Document getPage(String pageUrlOrHtmlPath) {
        try {
            log.trace("Try to get page from url '" + pageUrlOrHtmlPath + "'; UserAgent '" + USER_AGENT + "'");
            return getPageFromUrl(pageUrlOrHtmlPath);
        } catch (IllegalArgumentException e) {
            log.trace("Try to get page from path '" + pageUrlOrHtmlPath + "'");
            return getPageFromPath(pageUrlOrHtmlPath);
        }
    }


    /**
     * Returns schedule page.
     *
     * @param source the source object
     * @return the schedule page
     */
    protected static Document getSchedulePage(Source source) {
        return getPage(Context.getPages().getSchedulePageUrl(source.getLinkParameter()));
    }

    /**
     * Returns news page.
     *
     * @return the news page
     */
    protected static Document getNewsPage() {
        return getPage(Context.getPages().getNewsPageUrl());
    }

    protected static Document getSourceListPage() {
        return getPage(Context.getPages().getSourceListPageUrl());
    }
}
