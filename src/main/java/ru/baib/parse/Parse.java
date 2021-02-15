package ru.baib.parse;

import ru.baib.model.Post;

import java.util.List;

public interface Parse {
    List<Post> list(String link);
    Post detail(String link);
    List<String> getLinks();
}
