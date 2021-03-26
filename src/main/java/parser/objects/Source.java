package parser.objects;

import lombok.Data;
import parser.enums.SourceType;

@Data
public class Source {
    private String value;
    private String link;
    private SourceType type;
}
