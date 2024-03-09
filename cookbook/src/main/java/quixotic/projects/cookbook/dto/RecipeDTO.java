package quixotic.projects.cookbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.Ingredient;
import quixotic.projects.cookbook.model.Publication;
import quixotic.projects.cookbook.model.Recipe;
import quixotic.projects.cookbook.model.enums.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static quixotic.projects.cookbook.validation.Validation.validateRecipe;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeDTO {
    private Long id;
    private String title;
    private String description;
    private String cookUsername;
    private Visibility visibility;
    private Set<String> instructions = new HashSet<>();
    private Set<IngredientDTO> ingredients = new HashSet<>();
    private RecipeType category;
    private DifficultyLevel difficulty;
    private int serving;
    private PortionSize portionSize;
    private List<DietType> dietTypes = new ArrayList<>();
    private float prepTime;
    private float cookTime;

    public RecipeDTO(Recipe recipe) {
        this.id = recipe.getId();
        this.title = recipe.getTitle();
        this.description = recipe.getDescription();
        this.cookUsername = recipe.getCook().getUsername();
        this.visibility = recipe.getVisibility();
        this.instructions = recipe.getInstructions();
        this.ingredients = recipe.getIngredients().stream().map(IngredientDTO::new).collect(Collectors.toSet());
        this.category = recipe.getCategory();
        this.difficulty = recipe.getDifficulty();
        this.serving = recipe.getServing();
        this.portionSize = recipe.getPortionSize();
        this.dietTypes = recipe.getDietTypes();
        this.prepTime = recipe.getPrepTime();
        this.cookTime = recipe.getCookTime();
    }

    public Recipe toEntity(Cook cook) {
        validateRecipe(this);
        return Recipe.builder()
                .title(this.title)
                .description(this.description)
                .visibility(this.visibility)
                .cook(cook)
                .instructions(this.instructions)
                .ingredients(this.ingredients.stream().map(IngredientDTO::toEntity).collect(Collectors.toSet()))
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
