package ru.baib;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;

public class SqlRuParse {

    ArrayList<String> links = new ArrayList<>();

    public SqlRuParse() {
        links.add("https://www.sql.ru/forum/job-offers");
        links.add("https://www.sql.ru/forum/job-offers/2");
        links.add("https://www.sql.ru/forum/job-offers/3");
        links.add("https://www.sql.ru/forum/job-offers/4");
        links.add("https://www.sql.ru/forum/job-offers/5");
    }

    public ArrayList<String> getLinks() {
        return this.links;
    }

    public static void main(String[] args) throws Exception {
        SqlRuParse sqlRuParse = new SqlRuParse();
        for (String str: sqlRuParse.getLinks()) {
            Document doc = Jsoup.connect(str).get();
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
}
