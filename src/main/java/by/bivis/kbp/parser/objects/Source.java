package by.bivis.kbp.parser.objects;

import by.bivis.kbp.parser.enums.SourceType;
import by.bivis.kbp.parser.objects.schedule.Schedule;
import by.bivis.kbp.parser.objects.schedule.ScheduleLesson;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table (name = "sources")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Source implements Serializable {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(mappedBy = "lessons_sources")
    List<ScheduleLesson> lessons;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Schedule schedule;

    @Column(name = "value")
    private String value;

    @Column(name = "link_parameter")
    private String linkParameter;

    @Column(name = "type")
    private SourceType type;

    @Column(name = "parsing_date")
    private long parsingDate;

    public Source(String value, String linkParameter, SourceType type) {
        this.value = value;
        this.linkParameter = linkParameter;
        this.type = type;
    }

    // TODO add getSubCategory method
}
