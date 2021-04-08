package by.bivis.kbp.parser.objects.schedule;

import by.bivis.kbp.parser.objects.Source;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static by.bivis.kbp.parser.utils.DateUtils.getDayNumber;

@Entity
@Table (name = "schedules")
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode
public class Schedule {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "parsing_date")
    private long parsingDate;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Source source;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "schedules_columns",
    joinColumns = @JoinColumn(name = "schedule_id"),
    inverseJoinColumns = @JoinColumn(name = "column_id"))
    private List<ScheduleColumn> columns;

    public Schedule(long parsingDate, Source source, List<ScheduleColumn> columns) {
        this.parsingDate = parsingDate;
        this.source = source;
        this.columns = columns;
    }

    /**
     * Returns today schedule. On Sunday returns Monday's schedule
     *
     * @return the today schedule
     */
    public ScheduleColumn getTodaySchedule() {
        return columns.get(getDayNumber(LocalDate.now()) - 1);
    }

    /**
     * Returns today and tomorrow schedule. On Sunday returns Monday's and Tuesday's schedule
     *
     * @return the today and tomorrow schedule list
     */
    public List<ScheduleColumn> getTodayAndTomorrowSchedule() {
        return Arrays.asList(getTodaySchedule(), columns.get(getDayNumber(LocalDate.now())));
    }
}
