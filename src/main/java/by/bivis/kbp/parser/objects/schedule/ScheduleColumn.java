package by.bivis.kbp.parser.objects.schedule;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ScheduleColumn {
    private List<ScheduleCell> cellList = new ArrayList<>();
    private boolean isApproved;
}
