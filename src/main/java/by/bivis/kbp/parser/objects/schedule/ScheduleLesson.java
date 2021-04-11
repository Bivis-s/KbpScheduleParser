package by.bivis.kbp.parser.objects.schedule;

import by.bivis.kbp.parser.enums.SourceType;
import by.bivis.kbp.parser.objects.Source;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@EqualsAndHashCode
public class ScheduleLesson {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "lessons", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Source> sourceList;

    @ManyToOne
    @JoinColumn(name = "cell_id")
    private ScheduleCell cell;

    public List<Source> getSourcesByType(SourceType type) {
        List<Source> sourcesByType = new ArrayList<>();
        for (Source source : sourceList) {
            if (source.getType() == type) {
                sourcesByType.add(source);
            }
        }
        return sourcesByType;
    }

    public ScheduleLesson() {
    }

    public List<Source> getSourceList() {
        if (sourceList == null) {
            sourceList = new ArrayList<>();
        }
        return sourceList;
    }

    @Override
    public String toString() {
        return "ScheduleLesson{" +
                "id=" + id +
                ", sourceList=" + sourceList +
                ", cell=" + cell +
                '}';
    }
}
