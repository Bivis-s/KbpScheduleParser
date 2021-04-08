package by.bivis.kbp.parser.objects.schedule;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cells")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ScheduleCell {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "cells_lessons",
            joinColumns = @JoinColumn(name = "cell_id"),
            inverseJoinColumns = @JoinColumn(name = "lesson_id"))
    private List<ScheduleLesson> lessons = new ArrayList<>();

    @ManyToMany(mappedBy = "schedules_columns")
    private List<ScheduleColumn> columns;

    public boolean isEmpty() {
        return lessons.isEmpty();
    }
}
