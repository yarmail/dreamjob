package project.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.Post;
import project.store.PostDBStore;
import java.util.List;

@ThreadSafe
@Service
public class PostService {
    private final PostDBStore postDBStore;
    private final CityService cityService;

    @Autowired
    public PostService(PostDBStore postDBStore, CityService cityService) {
        this.postDBStore = postDBStore;
        this.cityService = cityService;
    }

    public void add(Post post) {
        postDBStore.add(post);
    }

    public List<Post> findAll() {
        List<Post> posts = postDBStore.findAll();
        posts.forEach(
                post -> post.setCity(
                        cityService.findById(post.getCity().getId())
                )
        );
        return posts;
    }

    public Post findById(int id) {
        return postDBStore.findById(id);
    }

    public void replace(Post post) {
        postDBStore.replace(post);
    }
}