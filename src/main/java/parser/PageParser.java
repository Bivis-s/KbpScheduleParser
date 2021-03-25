package parser;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import parser.enums.Page;

import java.io.IOError;
import java.io.IOException;

@Log4j2
public class PageParser {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36";

    private PageParser() {
    }

    public static Document getPage(Page page) {
        log.debug("Get page '" + page.getValue() + "'; UserAgent '" + USER_AGENT + "'");
        try {
            return Jsoup
                    .connect(page.getValue())
                    .userAgent(USER_AGENT)
                    .get();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new IOError(e);
        }
    }
}
