package by.bivis.kbp.parser.objects.schedule;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "columns")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class ScheduleColumn {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "column")
    private List<ScheduleCell> cellList = new ArrayList<>();

    @Column(name = "is_approved")
    private boolean isApproved;

    @Column(name = "day_number")
    private int dayNumber;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Override
    public String toString() {
        return "ScheduleColumn{" +
                "id=" + id +
                ", cellList=" + cellList +
                ", isApproved=" + isApproved +
                ", dayNumber=" + dayNumber +
                ", schedule=" + schedule +
                '}';
    }

    //TODO add dayOfWeekLabel (where 1 is monday)
}
