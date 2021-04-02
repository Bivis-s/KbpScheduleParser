package by.bivis.kbp.parser.parsers;

import by.bivis.kbp.parser.enums.SourceType;
import by.bivis.kbp.parser.objects.Source;
import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;

import java.util.List;

import static by.bivis.kbp.parser.parsers.PageParser.getSourceListPage;
import static org.testng.Assert.assertEquals;

@Log4j2
public class SourceParserTest extends BaseParserTest {
    @Test
    public void getSourceListTest() {
        List<Source> sourceList = SourceParser.getAvailableSourceList(getSourceListPage());
        assertEquals(sourceList.size(), 364);
    }

    @Test
    public void sourceContentTest() {
        Source source = SourceParser.getAvailableSourceList(getSourceListPage()).get(2);
        assertEquals(source.getType(), SourceType.AUDIENCE);
        assertEquals(source.getLinkParameter(), "?cat=place&id=28");
        assertEquals(source.getValue(), "25");
    }
}