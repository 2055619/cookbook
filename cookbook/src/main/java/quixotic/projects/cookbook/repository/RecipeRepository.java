package quixotic.projects.cookbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quixotic.projects.cookbook.model.Recipe;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findByTitle(String title);

    void deleteByTitle(String title);
}
