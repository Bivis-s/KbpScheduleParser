package by.bivis.kbp.parser.parsers;

import by.bivis.kbp.parser.enums.Page;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOError;
import java.io.IOException;

@Log4j2
public final class PageParser {
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

    public static Document getPage(String pageUrlOrHtmlPath) {
        try {
            log.debug("Try to get page from url '" + pageUrlOrHtmlPath + "'; UserAgent '" + USER_AGENT + "'");
            return getPageFromUrl(pageUrlOrHtmlPath);
        } catch (IllegalArgumentException e) {
            log.debug("Try to get page from path '" + pageUrlOrHtmlPath + "'");
            return getPageFromPath(pageUrlOrHtmlPath);
        }
    }

    public static Document getPage(Page page, String stringForConcatenation) {
        log.debug("Get page '" + page.getValue() + stringForConcatenation + "'; UserAgent '" + USER_AGENT + "'");
        try {
            return Jsoup
                    .connect(page.getValue(stringForConcatenation))
                    .userAgent(USER_AGENT)
                    .get();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IOError(e);
        }
    }
}
