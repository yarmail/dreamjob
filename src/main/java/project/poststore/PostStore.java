package project.poststore;

import project.postmodel.Post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Создадим хранилище PostStore. Оно будет синглтон
 */
public class PostStore {
    private static final PostStore INST = new PostStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();

    public PostStore() {
        posts.put(1, new Post(1, "Junior Java Job", "description1"));
        posts.put(2, new Post(2, "Middle Java Job", "description2"));
        posts.put(3, new Post(3, "Senior Java Job", "description3"));
    }

    public static PostStore instOf() {
        return INST;
    }

    public void add(Post post) {
        posts.putIfAbsent(post.getId(), post);
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void update(Post post) {
        posts.put(post.getId(), post);
    }
}