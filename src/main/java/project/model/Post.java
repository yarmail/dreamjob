package project.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Вакансия
 * name - название вакансии
 * description - описание вакансии
 *
 * created = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)
 *
 */

public class Post {
    private int id;
    private String name;
    private String description;
    private LocalDateTime created =
            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);

    public Post() {
    }

    public Post(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Post(int id, String name, String description, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Post{"
                + "id=" + id
                + ", name='" + name
                + '\'' + ", description='" + description
                + '\'' + ", created=" + created + '}';
    }
}