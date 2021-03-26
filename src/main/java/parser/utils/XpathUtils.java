package parser.utils;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.xsoup.Xsoup;

public class XpathUtils {
    private XpathUtils() {
    }

    public static Elements getInnerElements(Element element, String xpath) {
        return Xsoup.compile(xpath).evaluate(element).getElements();
    }

    public static Element getInnerElement(Element element, String xpath) {
        return Xsoup.compile(xpath).evaluate(element).getElements().get(0);
    }

    public static String getHrefAttribute(Element element) {
        return element.attr("href");
    }

    public static String getSrcAttribute(Element element) {
        return element.attr("src");
    }
}
