package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.City;

@Repository
public class CandidateDBStore {

    private final static String FIND_ALL = "SELECT * FROM candidate ORDER BY id";

    private final static String ADD =
    "INSERT INTO candidate(name, description, created, photo, city_id) VALUES (?, ?, ?, ?, ?)";

    private final static String UPDATE =
    "UPDATE candidate SET name = ?, description = ?, created = ?, photo = ?, city_id = ? WHERE id = ?";

    private final static String FIND_BY_ID = "SELECT * FROM candidate WHERE id = ?";
    private final static String DELETE = "DELETE FROM candidate WHERE id = ?";
    private static final Logger LOG = Logger.getLogger(CandidateDBStore.class);
    private final BasicDataSource pool;

    public CandidateDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Candidate> findAll() {
        List<Candidate> candidates = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_ALL)) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    candidates.add(getCandidate(it));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in  findAll() method", e);
        }
        return candidates;
    }

    public Candidate add(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(ADD,
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setTimestamp(3,  Timestamp.valueOf(candidate.getCreated()));
            ps.setBytes(4, (candidate.getPhoto()));
            ps.setInt(5, candidate.getCity().getId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in  add() method", e);
        }
        return candidate;
    }

    public void replace(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(UPDATE)) {
            ps.setString(1, candidate.getName());
            ps.setString(2, candidate.getDescription());
            ps.setTimestamp(3,  Timestamp.valueOf(candidate.getCreated()));
            ps.setBytes(4, candidate.getPhoto());
            ps.setInt(5, candidate.getCity().getId());
            ps.setInt(6, candidate.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error("Exception in  update() method", e);
        }
    }

    public Candidate findById(int id) {
        Candidate result = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(FIND_BY_ID)) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    result = getCandidate(it);
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in  findById() method", e);
        }
        return result;
    }

    public void delete(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =
                     cn.prepareStatement(DELETE)) {
            ps.setInt(1, candidate.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            LOG.error("Exception in  delete() method", e);
        }
    }

    private Candidate getCandidate(ResultSet resultset) throws SQLException {
        return new Candidate(
                resultset.getInt("id"),
                resultset.getString("name"),
                resultset.getString("description"),
                resultset.getTimestamp("created").toLocalDateTime(),
                new City(resultset.getInt("city_id"), ""),
                resultset.getBytes("photo"));
    }
}