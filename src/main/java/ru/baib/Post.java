package ru.baib;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post {
    String link;
    String description;
    LocalDateTime creationDate;

    public Post() {

    }

    public Post(String link, String description, LocalDateTime creationDate) {
        this.link = link;
        this.description = description;
        this.creationDate = creationDate;
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
        return link.equals(post.link) && description.equals(post.description) && creationDate.equals(post.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link, description, creationDate);
    }

    @Override
    public String toString() {
        return "Post{"
                +
                "link='" + link + '\''
                +
                ", description='" + description + '\''
                +
                ", creationDate=" + creationDate
                +
                '}';
    }
}
