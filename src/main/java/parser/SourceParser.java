package parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import parser.enums.Page;
import parser.enums.SourceType;
import parser.objects.Source;

import java.util.ArrayList;
import java.util.List;

import static parser.PageParser.getPage;
import static parser.utils.JsoupUtils.*;

public class SourceParser {
    private SourceParser() {
    }

    private static Elements getSourceElements() {
        Document document = getPage(Page.SOURCE_LIST);
        return getInnerElements(document, Selector.SOURCE_LINE_SELECTOR);
    }

    private static Source createSource(Element element) {
        Source source = new Source();
        Element sourceTitle = getInnerElement(element, Selector.SOURCE_TITLE_SELECTOR);
        source.setType(SourceType.getEnumByValue(sourceTitle.text().trim()));
        Element sourceValue = getInnerElement(element, Selector.SOURCE_VALUE_SELECTOR);
        source.setValue(sourceValue.text().trim());
        source.setLink(getHrefAttribute(sourceValue));
        return source;
    }

    private static List<Source> createSourceList(Elements elements) {
        List<Source> sourceList = new ArrayList<>();
        for (Element element : elements) {
            sourceList.add(createSource(element));
        }
        return sourceList;
    }

    /**
     * Returns the source objects from the college site search page
     *
     * @return the source object list
     */
    public static List<Source> getSourceList() {
        return createSourceList(getSourceElements());
    }

    private static class Selector {
        private static final String SOURCE_LINE_SELECTOR = "[class~=block_back] [class~=text_h1]:first-of-type ~ *";
        private static final String SOURCE_TITLE_SELECTOR = "span";
        private static final String SOURCE_VALUE_SELECTOR = "a";
    }
}
