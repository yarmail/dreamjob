package ru.job4j.dreamjob.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.util.UserSession;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    /**
     * см UserController.login
     * HttpSession session = request.getSession();
     * session.setAttribute("user", userDb.get());
     * Теперь данные записанные в HttpSession
     * можно получить на другой странице.
     */
    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        User user = UserSession.getUser(session);
        model.addAttribute("user", user);
        return "index";
    }

    /**
     * Осталось настроить выход из системы.
     * Чтобы его сделать нужно использовать метод HttpSession.invalidate
     * - это метод удалить все связанные с этим пользователем данные.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}