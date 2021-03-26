package parser.objects;

import lombok.Data;

@Data
public class News {
    private String title;
    private String caption;
    private String articleLink;
    private String imgLink;
}
