package quixotic.projects.cookbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quixotic.projects.cookbook.model.Publication;

import java.util.Optional;


public interface PublicationRepository extends JpaRepository<Publication, Long> {
    Optional<Publication> findByTitle(String title);
}
