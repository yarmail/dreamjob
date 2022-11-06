package project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import project.model.Post;
import project.storage.PostStore;


@Controller
public class PostController {
    private final PostStore postStore = PostStore.instOf();

    /**
     * Метод posts принимает объект Model.
     * Он используется Thymeleaf для поиска объектов,
     * которые нужны отобразить на виде.
     * В Model мы добавляем объект posts
     * Контроллер заполняет Model и передает два объекта в Thymeleaf –
     * Model и View(posts.html). Thymeleaf генерирует HTML и возвращает ее клиенту.
     *
     * Проверка: localhost:8080/posts
     */
    @GetMapping ("/posts")
    public String posts(Model model) {
        model.addAttribute("posts", postStore.findAll());
        return "posts";
    }

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/formAddPost")
    public String addPost(Model model) {
        model.addAttribute("post", new Post());
        return "addPost";
    }
}