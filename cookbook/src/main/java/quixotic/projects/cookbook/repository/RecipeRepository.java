package quixotic.projects.cookbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quixotic.projects.cookbook.model.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
