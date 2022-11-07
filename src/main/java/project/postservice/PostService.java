package project.postservice;

import project.postmodel.Post;
import project.poststore.PostStore;
import java.util.Collection;

public class PostService {
    private static final PostService INST = new PostService();
    private static final PostStore POST_STORE = PostStore.instOf();

    public static PostService instOf() {
        return INST;
    }

    public void add(Post post) {
        POST_STORE.add(post);
    }

    public Collection<Post> findAll() {
        return POST_STORE.findAll();
    }

    public Post findById(int id) {
        return POST_STORE.findById(id);
    }

    public void update(Post post) {
        POST_STORE.update(post);
    }
}