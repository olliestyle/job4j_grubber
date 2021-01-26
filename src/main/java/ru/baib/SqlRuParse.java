package ru.baib;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
}
