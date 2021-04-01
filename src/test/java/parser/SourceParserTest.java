package parser;

import lombok.extern.log4j.Log4j2;
import org.testng.annotations.Test;
import parser.enums.SourceType;
import parser.objects.Source;

import java.util.List;

import static org.testng.Assert.assertEquals;

@Log4j2
public class SourceParserTest extends BaseParserTest {
    @Test
    public void getSourceListTest() {
        List<Source> sourceList = SourceParser.getSourceList();
        assertEquals(sourceList.size(), 364);
    }

    @Test
    public void sourceContentTest() {
        Source source = SourceParser.getSourceList().get(2);
        assertEquals(source.getType(), SourceType.AUDIENCE);
        assertEquals(source.getLinkParameter(), "?cat=place&id=28");
        assertEquals(source.getValue(), "25");
    }
}