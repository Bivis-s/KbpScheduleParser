package by.bivis.kbp.parser.utils;

import by.bivis.kbp.parser.parsers.BaseParserTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static org.testng.Assert.assertEquals;

public class DateUtilsTest extends BaseParserTest {
    @Test
    public void getDayNumberTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int expectedDayNumber = -1;
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                expectedDayNumber = 7;
                break;
            case 2:
                expectedDayNumber = 1;
                break;
            case 3:
                expectedDayNumber = 2;
                break;
            case 4:
                expectedDayNumber = 3;
                break;
            case 5:
                expectedDayNumber = 4;
                break;
            case 6:
                expectedDayNumber = 5;
                break;
            case 7:
                expectedDayNumber = 6;
                break;
        }
        int actualDayNumber = DateUtils.getDayNumber(LocalDate.now());
        assertEquals(actualDayNumber, expectedDayNumber);
    }

    @Test
    public void printDayNumber() {
        System.out.println(DateUtils.getDayNumber(LocalDate.now()));
    }
}