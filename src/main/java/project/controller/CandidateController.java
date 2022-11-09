package project.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.model.Candidate;
import project.service.CandidateService;
import project.service.CityService;
import java.io.IOException;

/**
 * Проверка: localhost:8080/candidates
 */
@ThreadSafe
@Controller
public class CandidateController {
    private final CandidateService candidateService;
    private final CityService cityService;

    @Autowired
    public CandidateController(CandidateService candidateService, CityService cityService) {
        this.candidateService = candidateService;
        this.cityService = cityService;
    }

    @GetMapping("/candidates")
    public String candidates(Model model) {
        model.addAttribute("candidates", candidateService.findAll());
        return "candidates";
    }

    @GetMapping("/formAddCandidate")
    public String addCandidate(Model model) {
        model.addAttribute("cities", cityService.getAllCities());
        return "addCandidate";
    }

    /**
     *  HTTP протокол передает файл в виде строки,
     *  закодированной в кодировке BASE64.
     *  Spring не умеет напрямую конвертировать эту строку
     *  в массив байт, поэтому в форме поля file имеет имя
     *  отличное от поля объекта Candidate.photo.
     *  Если вы назовете их одинаково Spring завершит
     *  работу с ошибкой - несоответствие типов данных.
     *
     */
    @PostMapping("/createCandidate")
    public String createCandidate(
            @ModelAttribute Candidate candidate,
            @RequestParam("file") MultipartFile file)
            throws IOException {
        int cityId = candidate.getCity().getId();
        candidate.setCity(cityService.findById(cityId));
        candidate.setPhoto(file.getBytes());
        candidateService.add(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidate/{candidateId}")
    public String formUpdateCandidate(Model model, @PathVariable("candidateId") int id) {
        model.addAttribute("candidate", candidateService.findById(id));
        model.addAttribute("cities", cityService.getAllCities());
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate")
    public  String updateCandidate(
            @ModelAttribute Candidate candidate,
            @RequestParam("file") MultipartFile file)
            throws IOException {
        int cityId = candidate.getCity().getId();
        candidate.setCity(cityService.findById(cityId));
        candidateService.replace(candidate);
        return "redirect:/candidates";
    }

    /**
     * Здесь используется класс RepsonseEntity
     * для формирования ответа.
     * В нем нужно указать тип ответа content-type для любого
     * файла это будет "application/octet-stream".
     * Когда браузер загружает тег img он отправляет новый
     * запрос на сервер по адресу указанному в атрибуте src.
     * Сервер преобразует массив байт в строку в кодировке BASE64.
     * В свою очередь браузер преобразует ее в изображение.
     */
    @GetMapping("/photoCandidate/{candidateId}")
    public ResponseEntity<Resource> download(@PathVariable("candidateId") Integer candidateId) {
        Candidate candidate = candidateService.findById(candidateId);
        return ResponseEntity.ok()
                .headers(new HttpHeaders())
                .contentLength(candidate.getPhoto().length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new ByteArrayResource(candidate.getPhoto()));
    }
}