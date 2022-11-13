package project.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.model.Post;
import project.service.CityService;
import project.service.PostService;

@ThreadSafe
@Controller
public class PostController {
    private final PostService postService;
    private final CityService cityService;

    public PostController(PostService postService, CityService cityService) {
        this.postService = postService;
        this.cityService = cityService;
    }

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
        model.addAttribute("posts", postService.findAll());
        return "posts";
    }

    @GetMapping("/formAddPost")
    public String addPost(Model model) {
        model.addAttribute("cities", cityService.getAllCities());
        return "addPost";
    }

    /**
     * Старый вариант
     * public String createPost(HttpServletRequest req) {
     * String name = req.getParameter("name");
     * System.out.println(name);
     *
     * С формы у города приходит только его id,
     * поэтому прежде чем отправлять на сохранение наш
     * целевой объект - необходимо взять из сервиса город целиком, просетить(?)
     * его объекта и далее отправляем на сохранение Post
     */
    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post) {
        int cityId = post.getCity().getId();
        post.setCity(cityService.findById(cityId));
        postService.add(post);
        return "redirect:/posts";
    }

    /**
     *  этот метод обрабатывает заявку на редактирование
     *  Post, посылылает форму для редактирования,
     *  и модель для обновления
     *
     *  В ссылке к методу используется параметр.
     *  Он указывается в фигурных скобках.
     *  Spring будет переходить к этому методу по всем
     *  ссылками типа /formUpdatePost/${ЧИСЛО}.
     *  То есть у нас есть группа одинаковых действий.
     *  В списке вакансий ссылки будут отличаться только ID.
     *  Чтобы переменная postId стала доступной в методе ее нужно
     *  указать в параметрах к методу с помощью аннотации @PathVariable.
     *  Внутри аннотации указываем ключ из запроса "postId".
     *  Spring извлечет данные из URL и подставит их в запрос.
     *  Из запроса мы получаем ID и загружаем найденную вакансию в модель.
     */
    @GetMapping("/formUpdatePost/{postId}")
    public String formUpdatePost(Model model, @PathVariable("postId") int id) {
        model.addAttribute("post", postService.findById(id));
        model.addAttribute("cities", cityService.getAllCities());
        return "updatePost";
    }

    @PostMapping("/updatePost")
    public  String updatePost(@ModelAttribute Post post) {
        int cityId = post.getCity().getId();
        post.setCity(cityService.findById(cityId));
        postService.replace(post);
        return "redirect:/posts";
    }
}