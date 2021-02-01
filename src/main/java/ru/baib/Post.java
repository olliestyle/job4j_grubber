package ru.baib;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

public class Post {
    private int id;
    private String link;
    private String description;
    private LocalDateTime creationDate;

    public Post() {

    }

    public Post(int id, String link, String description, LocalDateTime creationDate) {
        this.id = id;
        this.link = link;
        this.description = description;
        this.creationDate = creationDate;
    }

    public void fillPost(String link) {
        this.setLink(link);
        // генерация id случайным образом, видимо, временная мера
        Random random = new Random();
        this.setId(random.nextInt());
        try {
            Document doc = Jsoup.connect(link).get();
            Elements desc = doc.select(".msgBody");
            this.setDescription(desc.get(1).text());
            Elements date = doc.select(".msgFooter");
            String fromFooter = date.get(0).text();
            this.setCreationDate(new DateParser().convertToLDT(fromFooter.substring(0, fromFooter.indexOf("[") - 1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return this.id;
    }

    public String getLink() {
        return this.link;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Post post = (Post) obj;
        return id == post.id && link.equals(post.link) && description.equals(post.description) && creationDate.equals(post.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, link, description, creationDate);
    }

    @Override
    public String toString() {
        return "Post{"
                +
                "id=" + id
                +
                ", link='" + link + '\''
                +
                ", description='" + description + '\''
                +
                ", creationDate=" + creationDate
                +
                '}';
    }
}
