package by.bivis.kbp.parser.objects;

import by.bivis.kbp.parser.enums.SourceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Source {
    private String value;
    private String linkParameter;
    private SourceType type;
}
