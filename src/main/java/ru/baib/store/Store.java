package ru.baib.store;

import ru.baib.model.Post;

import java.util.List;

public interface Store {
    void save(Post post);
    List<Post> getAll();
    Post findById(String id);
}
