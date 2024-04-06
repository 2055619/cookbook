package quixotic.projects.cookbook.dto;

import lombok.*;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.Recipe;
import quixotic.projects.cookbook.model.enums.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static quixotic.projects.cookbook.validation.Validation.validateRecipe;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeDTO extends PublicationDTO {
    private Set<String> instructions = new HashSet<>();
    private Set<IngredientDTO> ingredients = new HashSet<>();
    private RecipeType category;
    private DifficultyLevel difficulty;
    private int serving;
    private PortionSize portionSize;
    private List<DietType> dietTypes = new ArrayList<>();
    private float prepTime;
    private float cookTime;
    private byte[] image;

    @Builder
    public RecipeDTO(Long id, String title, String description, LocalDate localDate, Visibility visibility, String cookUsername, Set<String> instructions, Set<IngredientDTO> ingredients, RecipeType category, DifficultyLevel difficulty, int serving, PortionSize portionSize, List<DietType> dietTypes, float prepTime, float cookTime, byte[] image) {
        super(id, title, description, cookUsername, localDate, visibility);
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.category = category;
        this.difficulty = difficulty;
        this.serving = serving;
        this.portionSize = portionSize;
        this.dietTypes = dietTypes;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.image = image;
    }

    public RecipeDTO(Recipe recipe) {
        super(recipe);
        this.instructions = recipe.getInstructions();
        this.ingredients = recipe.getIngredients().stream().map(IngredientDTO::new).collect(Collectors.toSet());
        this.category = recipe.getCategory();
        this.difficulty = recipe.getDifficulty();
        this.serving = recipe.getServing();
        this.portionSize = recipe.getPortionSize();
        this.dietTypes = recipe.getDietTypes();
        this.prepTime = recipe.getPrepTime();
        this.cookTime = recipe.getCookTime();
        this.image = recipe.getImage();
    }

    public Recipe toEntity(Cook cook) {
        validateRecipe(this);
        return Recipe.builder()
                .title(this.getTitle())
                .description(this.getDescription())
                .visibility(this.getVisibility())
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
                .image(this.image)
                .build();
    }
}
