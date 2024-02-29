package quixotic.projects.cookbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cookbook.model.Ingredient;
import quixotic.projects.cookbook.model.Publication;
import quixotic.projects.cookbook.model.Recipe;
import quixotic.projects.cookbook.model.enums.DietType;
import quixotic.projects.cookbook.model.enums.DifficultyLevel;
import quixotic.projects.cookbook.model.enums.PortionSize;
import quixotic.projects.cookbook.model.enums.RecipeType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeDTO {
    private Set<String> instructions = new HashSet<>();
    private Set<Ingredient> ingredients;
    private RecipeType category;
    private DifficultyLevel difficulty;
    private int serving;
    private PortionSize portionSize;
    private List<DietType> dietTypes;
    private float prepTime;
    private float cookTime;

    public RecipeDTO(Recipe recipe) {
        this.instructions = recipe.getInstructions();
        this.ingredients = recipe.getIngredients();
        this.category = recipe.getCategory();
        this.difficulty = recipe.getDifficulty();
        this.serving = recipe.getServing();
        this.portionSize = recipe.getPortionSize();
        this.dietTypes = recipe.getDietTypes();
        this.prepTime = recipe.getPrepTime();
        this.cookTime = recipe.getCookTime();
    }

    public RecipeDTO(Publication publication) {
        Recipe recipe = (Recipe) publication;
        this.instructions = recipe.getInstructions();
        this.ingredients = recipe.getIngredients();
        this.category = recipe.getCategory();
        this.difficulty = recipe.getDifficulty();
        this.serving = recipe.getServing();
        this.portionSize = recipe.getPortionSize();
        this.dietTypes = recipe.getDietTypes();
        this.prepTime = recipe.getPrepTime();
        this.cookTime = recipe.getCookTime();
    }

    public Recipe toEntity() {
        return Recipe.builder()
                .instructions(this.instructions)
                .ingredients(this.ingredients)
                .category(this.category)
                .difficulty(this.difficulty)
                .serving(this.serving)
                .portionSize(this.portionSize)
                .dietTypes(this.dietTypes)
                .prepTime(this.prepTime)
                .cookTime(this.cookTime)
                .build();
    }
}
