package project.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import project.model.City;
import project.model.Post;

@Repository
public class PostDBStore {
    private static final Logger LOG = Logger.getLogger(PostDBStore.class);
    private final BasicDataSource pool;

    @Autowired
    public PostDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM post ORDER BY id")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    posts.add(getPost(resultSet));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return posts;
    }

    public Post add(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO post(name, description, created, visible, city_id) VALUES (?, ?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3,  Timestamp.valueOf(post.getCreated()));
            ps.setBoolean(4, post.isVisible());
            ps.setInt(5, post.getCity().getId());
            ps.execute();
            try (ResultSet resultSet = ps.getGeneratedKeys()) {
                if (resultSet.next()) {
                    post.setId(resultSet.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return post;
    }

    public boolean replace(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "UPDATE post SET name = ?, description = ?, visible = ?, city_id = ? WHERE id = ?")) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getDescription());
            ps.setTimestamp(3,  Timestamp.valueOf(post.getCreated()));
            ps.setBoolean(4, post.isVisible());
            ps.setInt(5, post.getCity().getId());
            ps.setInt(6, post.getId());
            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return false;
    }

    public Post findById(int id) {
        Post post = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM post WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    post = getPost(resultSet);
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return post;
    }

    public void delete(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "DELETE FROM post WHERE id = ?")) {
            ps.setInt(1, post.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    private Post getPost(ResultSet resultSet) throws SQLException {
        return new Post(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getTimestamp("created").toLocalDateTime(),
                resultSet.getBoolean("visible"),
                new City(resultSet.getInt("city_id"), ""));
    }
}