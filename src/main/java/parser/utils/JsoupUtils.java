package parser.utils;

import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Log4j2
public class JsoupUtils {
    private JsoupUtils() {
    }

    public static Elements getInnerElements(Element element, String selector) {
        return element.select(selector);
    }

    public static Element getInnerElement(Element element, String selector) {
        return getInnerElements(element, selector).get(0);
    }

    public static String getHrefAttribute(Element element) {
        return element.attr("href");
    }

    public static String getSrcAttribute(Element element) {
        return element.attr("src");
    }
}
