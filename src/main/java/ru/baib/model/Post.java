package ru.baib.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post {
    private int id;
    private String name;
    private String link;
    private String description;
    private LocalDateTime creationDate;

    public Post() {

    }

    public Post(int id, String name, String link, String description, LocalDateTime creationDate) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.description = description;
        this.creationDate = creationDate;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
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

    public void setName(String name) {
        this.name = name;
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
        return id == post.id && link.equals(post.link) && name.equals(post.name) && creationDate.equals(post.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, link, name, creationDate);
    }

    @Override
    public String toString() {
        return "Post{"
                +
                "id=" + id
                +
                ", name='" + name + '\''
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
