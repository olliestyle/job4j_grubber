package ru.baib;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;
import static org.junit.Assert.*;

public class SqlRuParseTest {

    SqlRuParse parser = new SqlRuParse();
    LocalDateTime ldt;

    @Test
    public void whenNow() {
        String str = "сегодня, 02:30";
        ldt = LocalDateTime.now();
        assertEquals(LocalDateTime.of(ldt.getYear(),
                ldt.getMonth(),
                ldt.getDayOfMonth(),
                2,
                30,
                0), parser.convertToLDT(str));
    }

    @Test
    public void whenTest() {
        String str = "22 янв 16, 10:56";
        assertEquals(LocalDateTime.of(2016,
                Month.JANUARY,
                22,
                10,
                56,
                0), parser.convertToLDT(str));
    }
}