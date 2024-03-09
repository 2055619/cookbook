package quixotic.projects.cookbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.summary.UserProfile;

import java.util.Optional;

public interface CookRepository extends JpaRepository<Cook, Long> {
    Optional<Cook> findCookByUsername(String username);
    Optional<UserProfile> findByUsername(String username);
}
