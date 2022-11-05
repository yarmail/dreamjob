package project.controller;

import org.springframework.ui.Model;
import project.storage.CandidateStore;

public class CandidateController {
    private final CandidateStore candidateStore = CandidateStore.instOf();

    public String candidates(Model model) {
        model.addAttribute("candidates", candidateStore.findAll());
        return "candidates";
    }
}
