package quixotic.projects.cookbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.Recipe;
import quixotic.projects.cookbook.model.summary.RecipeSummary;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findByTitle(String title);

    void deleteByTitle(String title);

    List<Recipe> findAllByTitleContainsIgnoreCase(String title);

    boolean existsByTitle(String title);
    List<Recipe> findRecipesByCook(Cook cook);
}
