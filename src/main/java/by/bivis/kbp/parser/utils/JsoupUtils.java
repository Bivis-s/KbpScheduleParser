package by.bivis.kbp.parser.utils;

import by.bivis.kbp.parser.errors.ElementNotFoundError;
import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Log4j2
public class JsoupUtils {
    private JsoupUtils() {
    }

    public static Elements getInnerElements(Element element, String selector) {
        try {
            return element.select(selector);
        } catch (IllegalArgumentException e) {
            throw new ElementNotFoundError("Elements is not found by selector " + selector);
        }
    }

    public static Element getInnerElement(Element element, String selector) throws IndexOutOfBoundsException {
        try {
            return getInnerElements(element, selector).get(0);
        } catch (IndexOutOfBoundsException e) {
            throw new ElementNotFoundError("Element is not found by selector " + selector);
        }
    }

    public static String getHrefAttribute(Element element) {
        return element.attr("href").trim();
    }

    public static String getSrcAttribute(Element element) {
        return element.attr("src").trim();
    }

    public static String getClassName(Element element) {
        return element.className();
    }
}
