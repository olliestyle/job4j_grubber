package ru.baib;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Iterator;

public class SqlRuParse {
    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        Elements dates = doc.select(".altCol");
        Iterator<Element> itRow = row.iterator();
        Iterator<Element> itDates = dates.iterator();
        itDates.next(); // у аттрибута altCol два значения, нам нужно только второе, где дата
        while (itRow.hasNext()) {
            Element href = itRow.next().child(0);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            Element date = itDates.next();
            System.out.println(date.text());
            if (itDates.hasNext()) {
                itDates.next();
            }
        }
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
            LocalDateTime ldt = LocalDateTime.now();
            year = ldt.getYear();
            month = ldt.getMonth();
            dayOfMonth = ldt.getDayOfMonth();
            if (dayOfMonth == 1 && month == Month.JANUARY) {
                year = year - 1;
                month = Month.DECEMBER;
                dayOfMonth = 31;
            } else if (dayOfMonth == 1) {
                month = Month.of(ldt.getMonthValue() - 1);
                dayOfMonth = defineDay(month);
            } else {
                dayOfMonth = dayOfMonth - 1;
            }
        } else {
            String[] spl = str.split(" ");
            year = Integer.parseInt("20" + spl[2].substring(0, 2));
            month = defineMonth(spl[1]);
            dayOfMonth = Integer.parseInt(spl[0]);
        }
        return LocalDateTime.of(year, month, dayOfMonth, hour, minute, 0);
    }

    private int defineDay(Month month) {
        int rsl = 30;
        if (month == Month.MARCH) {
            rsl = 28;
        } else if (month == Month.FEBRUARY
                || month == Month.APRIL
                || month == Month.JUNE
                || month == Month.SEPTEMBER
                || month == Month.NOVEMBER) {
            rsl = 31;
        }
        return rsl;
    }

    private Month defineMonth(String str) {
        Month res;
        switch (str) {
            case "фев":
                res = Month.FEBRUARY;
                break;
            case "мар":
                res = Month.MARCH;
                break;
            case "апр":
                res = Month.APRIL;
                break;
            case "май":
                res = Month.MAY;
                break;
            case "июн":
                res = Month.JUNE;
                break;
            case "июл":
                res = Month.JULY;
                break;
            case "авг":
                res = Month.AUGUST;
                break;
            case "сен":
                res = Month.SEPTEMBER;
                break;
            case "окт":
                res = Month.OCTOBER;
                break;
            case "ноя":
                res = Month.NOVEMBER;
                break;
            case "дек":
                res = Month.DECEMBER;
                break;
            default:
                res = Month.JANUARY;
        }
        return res;
    }
}
