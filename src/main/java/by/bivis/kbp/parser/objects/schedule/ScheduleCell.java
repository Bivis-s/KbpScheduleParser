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
    private long id;

    @OneToMany(mappedBy = "cell")
    private List<ScheduleLesson> lessons = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "column_id")
    private ScheduleColumn column;

    public boolean isEmpty() {
        return lessons.isEmpty();
    }

    @Override
    public String toString() {
        return "ScheduleCell{" +
                "id=" + id +
                ", lessons=" + lessons +
                ", column=" + column +
                '}';
    }
}
