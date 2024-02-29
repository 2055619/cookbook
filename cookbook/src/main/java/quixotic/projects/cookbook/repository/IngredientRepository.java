package quixotic.projects.cookbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quixotic.projects.cookbook.model.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
