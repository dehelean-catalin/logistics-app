package app.logisctics.repository;

import app.logisctics.dao.model.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DestinationRepository extends JpaRepository<Destination,Long> {
    public Optional<Destination> findByName(String name);

}
