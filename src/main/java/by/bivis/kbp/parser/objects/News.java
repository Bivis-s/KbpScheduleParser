package by.bivis.kbp.parser.objects;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "news")
@NoArgsConstructor
@Getter
@Setter
public class News {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return id == news.id &&
                Objects.equals(title, news.title) &&
                Objects.equals(caption, news.caption) &&
                Objects.equals(articleLink, news.articleLink) &&
                Objects.equals(imgLink, news.imgLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, caption, articleLink, imgLink);
    }
}
