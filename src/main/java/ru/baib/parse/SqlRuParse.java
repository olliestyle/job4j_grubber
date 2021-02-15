package ru.baib.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import ru.baib.DateParser;
import ru.baib.model.Post;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SqlRuParse implements Parse {

    private ArrayList<String> links = new ArrayList<>();

    public SqlRuParse() {
        links.add("https://www.sql.ru/forum/job-offers");
        links.add("https://www.sql.ru/forum/job-offers/2");
        links.add("https://www.sql.ru/forum/job-offers/3");
        links.add("https://www.sql.ru/forum/job-offers/4");
        links.add("https://www.sql.ru/forum/job-offers/5");
    }

    @Override
    public ArrayList<String> getLinks() {
        return this.links;
    }

    public static void main(String[] args) throws Exception {
        SqlRuParse sqlRuParse = new SqlRuParse();
        List<Post> allPosts = new ArrayList<>();
        for (String s: sqlRuParse.getLinks()) {
            allPosts.addAll(sqlRuParse.list(s));
        }
        System.out.println("debug");
    }

    @Override
    public List<Post> list(String link) {
        List<Post> res = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(link).get();
            Elements row = doc.select(".postslisttopic");
            for (Element el: row) {
                Post toAdd = detail(el.child(0).attr("href"));
                res.add(toAdd);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Post detail(String link) {
        Post toFill = new Post();
        toFill.setLink(link);
//        // генерация id случайным образом, видимо, временная мера
//        Random random = new Random();
//        toFill.setId(random.nextInt());
        try {
            Document doc = Jsoup.connect(link).get();
            List<TextNode> name = doc.select(".messageHeader").get(0).textNodes();
            toFill.setName(name.get(0).text().trim());
            Elements desc = doc.select(".msgBody");
            toFill.setDescription(desc.get(1).text());
            Elements date = doc.select(".msgFooter");
            String fromFooter = date.get(0).text();
            toFill.setCreationDate(new DateParser().convertToLDT(fromFooter.substring(0, fromFooter.indexOf("[") - 1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return toFill;
    }
}
