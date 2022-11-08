package project.candidatecontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.candidatemodel.Candidate;
import project.candidateservice.CandidateService;

/**
 * Проверка: localhost:8080/candidates
 */
@Controller
public class CandidateController {
    private static final CandidateService CANDIDATE_SERVICE = CandidateService.instOf();

    @GetMapping("/candidates")
    public String candidates(Model model) {
        model.addAttribute("candidates", CANDIDATE_SERVICE.findAll());
        return "candidates";
    }

    @GetMapping("/formAddCandidate")
    public String addCandidate(Model model) {
        model.addAttribute("candidate", new Candidate());
        return "addCandidate";
    }

    @PostMapping("/createCandidate")
    public String createCandidate(@ModelAttribute Candidate candidate) {
        CANDIDATE_SERVICE.add(candidate);
        return "redirect:/candidates";
    }

    @GetMapping("/formUpdateCandidate/{candidateId}")
    public String formUpdateCandidate(Model model, @PathVariable("candidateId") int id) {
        model.addAttribute("candidate", CANDIDATE_SERVICE.findById(id));
        return "updateCandidate";
    }

    @PostMapping("/updateCandidate")
    public  String updateCandidate(@ModelAttribute Candidate candidate) {
        CANDIDATE_SERVICE.replace(candidate);
        return "redirect:/candidates";
    }
}