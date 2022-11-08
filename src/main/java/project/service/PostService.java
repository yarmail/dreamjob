package project.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.Post;
import project.store.PostStore;
import java.util.Collection;

@ThreadSafe
@Service
public class PostService {
    private final PostStore postStore;

    @Autowired
    public PostService(PostStore postStore) {
        this.postStore = postStore;
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