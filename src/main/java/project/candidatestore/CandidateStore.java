package project.candidatestore;

import project.candidatemodel.Candidate;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CandidateStore {
    private static final CandidateStore INST = new CandidateStore();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    private CandidateStore() {
        candidates.put(id.incrementAndGet(), new Candidate(id.get(), "Candidate1", "description1"));
        candidates.put(id.incrementAndGet(), new Candidate(id.get(), "Candidate2", "description2"));
        candidates.put(id.incrementAndGet(), new Candidate(id.get(), "Candidate3", "description3"));
    }

    public static CandidateStore instOf() {
        return INST;
    }

    public void add(Candidate candidate) {
        candidate.setId(id.incrementAndGet());
        candidates.putIfAbsent(candidate.getId(), candidate);

    }

    public Collection<Candidate> findAll() {
        return candidates.values();
    }

    public Candidate findById(int id) {
        return candidates.get(id);
    }

    public void replace(Candidate candidate) {
        candidates.put(candidate.getId(), candidate);
    }
}