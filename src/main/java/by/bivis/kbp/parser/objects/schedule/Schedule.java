package by.bivis.kbp.parser.objects.schedule;

import by.bivis.kbp.parser.objects.Source;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static by.bivis.kbp.parser.utils.DateUtils.getKbpScheduleDayNumber;

@Entity
@Table(name = "schedules")
@NoArgsConstructor
@Getter
@Setter
public class Schedule {

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "parsing_date")
    private long parsingDate;

    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Source source;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
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
        return columns.get(getKbpScheduleDayNumber(LocalDate.now()) - 1);
    }

    /**
     * Returns today and tomorrow schedule. On Sunday returns Monday's and Tuesday's schedule
     *
     * @return the today and tomorrow schedule list
     */
    public List<ScheduleColumn> getTodayAndTomorrowSchedule() {
        return Arrays.asList(getTodaySchedule(), columns.get(getKbpScheduleDayNumber(LocalDate.now())));
    }

    public List<ScheduleColumn> getExtendedSchedule() {
        List<ScheduleColumn> extendedColumns = new ArrayList<>();
        int dayNumber = getKbpScheduleDayNumber(LocalDate.now());
        for (int i = dayNumber; i < dayNumber + 6; i++) {
            extendedColumns.add(columns.get(i - 1));
        }
        return extendedColumns;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return id == schedule.id &&
                Objects.equals(source, schedule.source) &&
                Objects.equals(columns, schedule.columns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, source, columns);
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", parsingDate=" + parsingDate +
                ", source=" + source +
                ", columns=" + columns +
                '}';
    }
}
