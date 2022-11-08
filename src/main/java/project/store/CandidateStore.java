package project.store;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import project.model.Candidate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class CandidateStore {
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    private CandidateStore() {
        candidates.put(id.incrementAndGet(), new Candidate(id.get(), "Candidate1", "description1"));
        candidates.put(id.incrementAndGet(), new Candidate(id.get(), "Candidate2", "description2"));
        candidates.put(id.incrementAndGet(), new Candidate(id.get(), "Candidate3", "description3"));
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