package ru.job4j.dreamjob.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.store.CandidateDBStore;

import java.util.List;

@ThreadSafe
@Service
public class CandidateService {
    private final CandidateDBStore candidateDBStore;
    private final CityService cityService;

    @Autowired
    public CandidateService(CandidateDBStore candidateStore, CityService cityService) {
        this.candidateDBStore = candidateStore;
        this.cityService = cityService;
    }

    public void add(Candidate candidate) {
        candidateDBStore.add(candidate);
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = candidateDBStore.findAll();
        candidates.forEach(
                post -> post.setCity(
                        cityService.findById(post.getCity().getId())
                )
        );
        return candidates;
    }

    public Candidate findById(int id) {
        return candidateDBStore.findById(id);
    }

    public void replace(Candidate candidate) {
        candidateDBStore.replace(candidate);
    }
}