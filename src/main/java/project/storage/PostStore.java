package project.storage;

import project.model.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Создадим хранилище PostStore. Оно будет синглтон
 */
public class PostStore {
    private static final PostStore INST = new PostStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    private PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "description1"));
        posts.put(2, new Post(2, "Middle Java Job", "description2"));
        posts.put(3, new Post(3, "Senior Java Job", "description3"));
    }

    public static PostStore instOf() {
        return INST;
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

}
