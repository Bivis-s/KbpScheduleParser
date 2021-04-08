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
    private int id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "columns_cells",
            joinColumns = @JoinColumn(name = "column_id"),
            inverseJoinColumns = @JoinColumn(name = "cell_id"))
    private List<ScheduleCell> cellList = new ArrayList<>();

    @ManyToMany(mappedBy = "schedules_columns")
    private List<Schedule> schedule;

    @Column(name = "is_approved")
    private boolean isApproved;

    //TODO add dayOfWeekLabel (where 1 is monday)
}
