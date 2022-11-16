package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * 2. Тестируем PostController [#504867]
 * Объект Post описывает модели логики приложения.
 * Этот объект мы можем использовать напрямую.
 *
 * Классы PostService, CityServiсe могут иметь
 * самостоятельные зависимости.
 * Поэтому для них мы используем mock - объект.
 *
 * Model - описывается интерфейсом. Для него мы тоже
 * используем объект заглушку mock.
 *
 * Метод PostController.posts принимает объект Model.
 * Добавляет в него список вакансий и возвращает имя вида.
 * Чтобы загрузить данные в PostService мы используем вызов mockito.
 *
 * Чтобы проверить данные в Model используем метод verify.
 * Здесь мы не может использовать классический assertThat,
 * потому что у нас нет объекта model, а есть только заглушка.
 *
 * Метод verify анализирует вызванные методы у объекта заглушки model.
 * Если коллекция posts переданные в модель и вызванная
 * у метода verify будут отличаться, то тест упадет с ошибкой.
 *
 */
class PostControllerTest {

    @Test
    public void posts() {
        User user = new User(1, "User1", "email", "password");
        List<Post> posts = List.of(
                new Post(1, "Post1", "description1", true, new City(1, "City1")),
                new Post(2, "Post2", "description2", false, new City(2, "City2"))
        );
        Model model = mock(Model.class);
        model.addAttribute("user", user);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        HttpSession session = mock(HttpSession.class);
        when(postService.findAll()).thenReturn(posts);
        PostController postController = new PostController(postService, cityService);
        String page = postController.posts(model, session);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("posts", posts);
        assertThat(page).isEqualTo("posts");
    }

    @Test
    public void formAddPost() {
        User user = new User(1, "User1", "email", "password");
        List<City> cities = List.of(new City(1, "City1"), new City(2, "City2"));
        Model model = mock(Model.class);
        model.addAttribute("user", user);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        HttpSession session = mock(HttpSession.class);
        when(cityService.getAllCities()).thenReturn(cities);
        PostController postController = new PostController(postService, cityService);
        String page = postController.addPost(model, session);
        verify(model).addAttribute("user", user);
        verify(model).addAttribute("cities", cities);
        assertThat(page).isEqualTo("addPost");
    }

    @Test
    public void createPost() {
        Post post = new Post(1, "Post1", "description1", true, new City(1, "City1"));
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);
        String page = postController.createPost(post);
        verify(postService).add(post);
        assertThat(page).isEqualTo("redirect:/posts");
    }

    @Test
    public void formUpdatePost() {
        List<Post> posts = List.of(
                new Post(1, "Post1", "description1", true, new City(1, "City1")),
                new Post(2, "Post2", "description2", false, new City(2, "City2"))
        );
        List<City> cities = List.of(new City(1, "City1"), new City(2, "City2"));
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(cityService.getAllCities()).thenReturn(cities);
        when(postService.findAll()).thenReturn(posts);
        PostController postController = new PostController(postService, cityService);
        String page = postController.formUpdatePost(model, posts.get(0).getId());
        verify(model).addAttribute("cities", cities);
        assertThat(page).isEqualTo("updatePost");
    }

    @Test
    public void updatePost() {
        List<Post> posts = List.of(new Post(1, "Post1", "description1", true, new City(1, "City1")));
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);
        String page = postController.updatePost(new Post(1, "Post2", "description2", true, new City(2, "City2")));
        verify(postService).replace(posts.get(0));
        assertThat(page).isEqualTo("redirect:/posts");
    }
}