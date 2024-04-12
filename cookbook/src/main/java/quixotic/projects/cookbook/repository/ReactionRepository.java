package quixotic.projects.cookbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.Publication;
import quixotic.projects.cookbook.model.Reaction;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    Optional<Reaction> findByCookAndPublication(Cook cook, Publication publication);

    List<Reaction> findAllByPublication(Publication publication);
    List<Reaction> findAllByPublicationId(Long publicationId);
}
