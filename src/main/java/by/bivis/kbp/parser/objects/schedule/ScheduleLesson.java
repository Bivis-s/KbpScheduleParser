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
    private int id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "lessons_sources",
            joinColumns = @JoinColumn(name = "lesson_id"),
            inverseJoinColumns = @JoinColumn(name = "source_id"))
    List<Source> sourceList;

    @ManyToMany(mappedBy = "cells_lessons")
    private List<ScheduleCell> cells;

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
        sourceList = new ArrayList<>();
    }
}
