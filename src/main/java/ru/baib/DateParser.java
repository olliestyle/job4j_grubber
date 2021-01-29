package ru.baib;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

public class DateParser {

    Map<String, Month> monthMap = new HashMap<>();

    public DateParser() {
        monthMap.put("янв", Month.JANUARY);
        monthMap.put("фев", Month.FEBRUARY);
        monthMap.put("мар", Month.MARCH);
        monthMap.put("апр", Month.APRIL);
        monthMap.put("май", Month.MAY);
        monthMap.put("июн", Month.JUNE);
        monthMap.put("июл", Month.JULY);
        monthMap.put("авг", Month.AUGUST);
        monthMap.put("сен", Month.SEPTEMBER);
        monthMap.put("окт", Month.OCTOBER);
        monthMap.put("ноя", Month.NOVEMBER);
        monthMap.put("дек", Month.DECEMBER);
    }

    public LocalDateTime convertToLDT(String str) {
        int year = 0;
        Month month;
        int dayOfMonth = 0;
        int hour = Integer.parseInt(str.substring(str.indexOf(",") + 2, str.indexOf(",") + 4));
        int minute = Integer.parseInt(str.substring(str.indexOf(":") + 1));
        if (str.startsWith("сегодня")) {
            LocalDateTime ldt = LocalDateTime.now();
            year = ldt.getYear();
            month = ldt.getMonth();
            dayOfMonth = ldt.getDayOfMonth();
        } else if (str.startsWith("вчера")) {
            LocalDateTime ldt = LocalDateTime.now().minusDays(1);
            year = ldt.getYear();
            month = ldt.getMonth();
            dayOfMonth = ldt.getDayOfMonth();
        } else {
            String[] spl = str.split(" ");
            year = Integer.parseInt("20" + spl[2].substring(0, 2));
            month = defineMonth(spl[1]);
            dayOfMonth = Integer.parseInt(spl[0]);
        }
        return LocalDateTime.of(year, month, dayOfMonth, hour, minute, 0);
    }

    private Month defineMonth(String str) {
        return monthMap.get(str);
    }
}
