package project.candidateservice;

import project.candidatemodel.Candidate;
import project.candidatestore.CandidateStore;
import java.util.Collection;

public class CandidateService {
    private static final CandidateService INST = new CandidateService();
    private final CandidateStore candidateStore = CandidateStore.instOf();

    public static CandidateService instOf() {
        return INST;
    }

    public void add(Candidate candidate) {
        candidateStore.add(candidate);
    }

    public Collection<Candidate> findAll() {
        return candidateStore.findAll();
    }

    public Candidate findById(int id) {
        return candidateStore.findById(id);
    }

    public void replace(Candidate candidate) {
        candidateStore.replace(candidate);
    }
}