package project.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import project.Main;
import project.model.City;
import project.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

class PostDBStoreTest {
    private static final PostDBStore STORE = new PostDBStore(new Main().loadPool());

    /* каждый тест очищаем таблицу от результатов */
    @BeforeEach
    private void init() {
        try (Connection cn = new Main().loadPool().getConnection();
             PreparedStatement statement = cn.prepareStatement(
                     "delete from post")) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void add() {
        Post post1 = new Post(0, "name1", "description1", true, new City(0, "city1"));
        STORE.add(post1);
        Post result = STORE.findById(post1.getId());
        assertThat(result).isEqualTo(post1);
    }

    @Test
    public void replace() {
        Post post1 = new Post(0, "name1", "description1", true, new City(0, "city1"));
        Post post2 = new Post(1, "name2", "description2", true, new City(1, "city2"));
        STORE.add(post1);
        post2.setId(post1.getId());
        STORE.replace(post2);
        Post result = STORE.findById(post1.getId());
        assertThat(result.getName()).isEqualTo(post2.getName());
    }

    @Test
    public void findAll() {
        Post post1 = new Post(0, "name1", "description1", true, new City(0, "city1"));
        Post post2 = new Post(1, "name2", "description2", true, new City(1, "city2"));
        STORE.add(post1);
        STORE.add(post2);
        List<Post> result = STORE.findAll();
        assertThat(result.size()).isEqualTo(2);
    }
}