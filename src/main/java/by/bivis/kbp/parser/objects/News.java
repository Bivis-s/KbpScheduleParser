package by.bivis.kbp.parser.objects;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "news")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class News {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "caption")
    private String caption;

    @Column(name = "article_link")
    private String articleLink;

    @Column(name = "img_link")
    private String imgLink;

    @Column(name = "parsing_date")
    private long parsingDate;
}
