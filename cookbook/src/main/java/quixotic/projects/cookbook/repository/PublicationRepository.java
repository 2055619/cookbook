package quixotic.projects.cookbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quixotic.projects.cookbook.model.Publication;


public interface PublicationRepository extends JpaRepository<Publication, Long> {
}
