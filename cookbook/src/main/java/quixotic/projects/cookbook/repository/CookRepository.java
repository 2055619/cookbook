package quixotic.projects.cookbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quixotic.projects.cookbook.model.Cook;

import java.util.Optional;

public interface CookRepository extends JpaRepository<Cook, Long> {
    Optional<Cook> findByUsername(String username);
    Optional<Cook> findByEmail(String email);
}
