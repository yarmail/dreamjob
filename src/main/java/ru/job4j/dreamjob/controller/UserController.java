package ru.job4j.dreamjob.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@ThreadSafe
public class UserController {
    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/formAddUser")
    public String addUser() {
        return "registration";
    }

    /**
     * Простой вариант валидации пользователя
     * без использования спец средств
     * Пользователь в системе должен быть уникальным.
     * При регистрации пользователя можно сделать поиск,
     * что такой почты еще нет.
     * Если ее нет, то зарегистрировать пользователя.
     *
     * Обработка запросов идет в отдельных нитях.
     * То есть вызов findUserByEmail для разных нитей
     * с одной и той же почтой вернет null.
     *
     * Чтобы не блокировать запросы мы будем использовать
     * CAS операции на уровне базы данных.
     * В базах данных есть механизм ограничений - constrains.
     * Их всего 3: not null, unique, check.
     *
     * В нашем примере поле почты должно быть уникальным.
     * Если две параллельные транзакции выполнять запрос
     * с одинаковой почтой, то та что будет быстрее выполниться,
     * а вторая вернется с ошибкой ConstrainsViolationException.
     */
    @PostMapping("/registration")
    public String registration(@ModelAttribute User user) {
        Optional<User> regUser = usersService.add(user);
        if (regUser.isEmpty()) {
            return "redirect:/fail";
        }
        return "redirect:/success";
    }

    @GetMapping("/fail")
    public String fail(Model model) {
        model.addAttribute("message", "Пользователь с такой почтой уже существует");
        return "fail";
    }

    @GetMapping("/success")
    public String success(Model model) {
        model.addAttribute("message", "Регистрация прошла успешно!");
        return "success";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    /**
     * Когда браузер отправляет запрос в tomcat создается объект HttpSession.
     * Этот объект связан с работой текущего пользователя.
     * Объект HttpSession можно получить через HttpServletRequest.
     *
     * Метод getSession возвращает объект HttpSession.
     * В нем можно хранить информацию о текущем пользователе.
     *
     * Чтобы добавить данные в HttpSession, используем метод
     * setAttribute(key, value). Чтобы получить данные из
     * HttpSession используется метод getAttribute(key).
     *
     * Обратите внимание, что внутри HttpSession используется
     * многопоточная коллекция ConcurrentHashMap. Это связано
     * с многопоточным окружением. Напомню, что для работы
     * с ConcurrentHashMap нельзя использовать операции
     * check-then-act. То есть HttpSession можно использовать
     * либо для записи, либо для чтения,
     * но нельзя делать это одновременно.
     *
     * Теперь данные записанные в HttpSession можно получить на другой странице.
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest request) {
        Optional<User> userDb = usersService.findUserByEmailAndPassword(
                user.getEmail(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }
}