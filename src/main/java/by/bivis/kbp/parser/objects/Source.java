package by.bivis.kbp.parser.objects;

import by.bivis.kbp.parser.enums.SourceType;
import by.bivis.kbp.parser.objects.schedule.Schedule;
import by.bivis.kbp.parser.objects.schedule.ScheduleLesson;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "sources")
@NoArgsConstructor
@Getter
@Setter
public class Source implements Serializable {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne()
    @JoinColumn(name = "lesson_id")
    private ScheduleLesson lessons;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Schedule schedule;

    @Column(name = "value")
    private String value;

    @Column(name = "link_parameter")
    private String linkParameter;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private SourceType type;

    @Column(name = "parsing_date")
    private long parsingDate;

    public Source(String value, String linkParameter, SourceType type) {
        this.value = value;
        this.linkParameter = linkParameter;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Source source = (Source) o;
        return id == source.id &&
                Objects.equals(lessons, source.lessons) &&
                Objects.equals(schedule, source.schedule) &&
                Objects.equals(value, source.value) &&
                Objects.equals(linkParameter, source.linkParameter) &&
                type == source.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lessons, schedule, value, linkParameter, type);
    }

    @Override
    public String toString() {
        return "Source{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", linkParameter='" + linkParameter + '\'' +
                ", type=" + type +
                ", parsingDate=" + parsingDate +
                '}';
    }

    // TODO add getSubCategory method
}
