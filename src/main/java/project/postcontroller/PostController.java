package project.postcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.postmodel.Post;
import project.postservice.PostService;

@Controller
public class PostController {
    private final PostService postService = PostService.instOf();

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
        model.addAttribute("post", new Post());
        return "addPost";
    }

    /**
     * Старый вариант
     * public String createPost(HttpServletRequest req) {
     * String name = req.getParameter("name");
     * System.out.println(name);
     */
    @PostMapping("/createPost")
    public String createPost(@ModelAttribute Post post) {
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
        return "updatePost";
    }

    @PostMapping("/updatePost")
    public  String updatePost(@ModelAttribute Post post) {
        postService.replace(post);
        return "redirect:/posts";
    }
}