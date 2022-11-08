package project.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import project.model.Post;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Создадим хранилище PostStore. Оно будет синглтон
 */
@ThreadSafe
@Repository
public class PostStore {
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public PostStore() {
        posts.put(id.incrementAndGet(), new Post(id.get(), "Junior Java Job", "description1"));
        posts.put(id.incrementAndGet(), new Post(id.get(), "Middle Java Job", "description2"));
        posts.put(id.incrementAndGet(), new Post(id.get(), "Senior Java Job", "description3"));
    }

    public void add(Post post) {
        post.setId(id.incrementAndGet());
        posts.putIfAbsent(post.getId(), post);
    }

    public Collection<Post> findAll() {
        return posts.values();
    }

    public Post findById(int id) {
        return posts.get(id);
    }

    public void replace(Post post) {
        posts.put(post.getId(), post);
    }
}