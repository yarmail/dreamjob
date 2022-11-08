package project.candidateservice;

import project.candidatemodel.Candidate;
import project.candidatestore.CandidateStore;
import java.util.Collection;

public class CandidateService {
    private static final CandidateService INST = new CandidateService();
    private static final CandidateStore CANDIDATE_STORE = CandidateStore.instOf();

    public static CandidateService instOf() {
        return INST;
    }

    public void add(Candidate candidate) {
        CANDIDATE_STORE.add(candidate);
    }

    public Collection<Candidate> findAll() {
        return CANDIDATE_STORE.findAll();
    }

    public Candidate findById(int id) {
        return CANDIDATE_STORE.findById(id);
    }

    public void replace(Candidate candidate) {
        CANDIDATE_STORE.replace(candidate);
    }
}