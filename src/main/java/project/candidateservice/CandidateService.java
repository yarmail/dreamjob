package project.candidateservice;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.candidatemodel.Candidate;
import project.candidatestore.CandidateStore;
import java.util.Collection;

@ThreadSafe
@Service
public class CandidateService {
    private final CandidateStore candidateStore;

    @Autowired
    public CandidateService(CandidateStore candidateStore) {
        this.candidateStore = candidateStore;
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