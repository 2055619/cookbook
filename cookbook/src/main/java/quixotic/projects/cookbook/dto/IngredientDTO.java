package quixotic.projects.cookbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cookbook.model.Ingredient;
import quixotic.projects.cookbook.model.enums.IngredientState;
import quixotic.projects.cookbook.model.enums.Unit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientDTO {
    private String name;
    private float amount;
    private Unit unit;
    private IngredientState ingredientState;
    private String recipeTitle;

    public IngredientDTO(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.amount = ingredient.getAmount();
        this.unit = ingredient.getUnit();
        this.ingredientState = ingredient.getIngredientState();
        this.recipeTitle = ingredient.getRecipe().getTitle();
    }

    public Ingredient toEntity() {
        return Ingredient.builder()
                .name(this.name)
                .amount(this.amount)
                .unit(this.unit)
                .ingredientState(this.ingredientState)
                .build();
    }
}
