package project.postservice;

import project.postmodel.Post;
import project.poststore.PostStore;
import java.util.Collection;

public class PostService {
    private static final PostService INST = new PostService();
    private final PostStore postStore = PostStore.instOf();

    public static PostService instOf() {
        return INST;
    }

    public void add(Post post) {
        postStore.add(post);
    }

    public Collection<Post> findAll() {
        return postStore.findAll();
    }

    public Post findById(int id) {
        return postStore.findById(id);
    }

    public void replace(Post post) {
        postStore.replace(post);
    }
}