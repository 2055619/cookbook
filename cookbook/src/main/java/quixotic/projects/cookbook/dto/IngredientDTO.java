package quixotic.projects.cookbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cookbook.model.Ingredient;
import quixotic.projects.cookbook.model.enums.IngredientState;
import quixotic.projects.cookbook.model.enums.Unit;

import static quixotic.projects.cookbook.validation.Validation.validateIngredient;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientDTO {
    private String name;
    private float quantity;
    private Unit unit;
    private IngredientState ingredientState;

    public IngredientDTO(Ingredient ingredient) {
        this.name = ingredient.getName();
        this.quantity = ingredient.getQuantity();
        this.unit = ingredient.getUnit();
        this.ingredientState = ingredient.getIngredientState();
    }

    public Ingredient toEntity() {
        validateIngredient(this);
        return Ingredient.builder()
                .name(this.name)
                .quantity(this.quantity)
                .unit(this.unit)
                .ingredientState(this.ingredientState)
                .build();
    }
}
