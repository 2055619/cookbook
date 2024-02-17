package quixotic.projects.cookbook.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public enum IngredientState {
    POWDER(List.of(Unit.TEASPOON, Unit.TABLESPOON, Unit.CUP, Unit.OUNCE, Unit.POUND, Unit.GRAM, Unit.KILOGRAM, Unit.PINCH, Unit.DASH)),
    LIQUID(List.of(Unit.TEASPOON, Unit.TABLESPOON, Unit.CUP, Unit.OUNCE, Unit.LITER, Unit.MILLILITER, Unit.DASH)),
    SOLID(List.of(Unit.GRAM, Unit.KILOGRAM, Unit.POUND, Unit.PINCH, Unit.EACH, Unit.NUMBER)),
    COUNTABLE(List.of(Unit.EACH, Unit.NUMBER)),
    OTHER("Other", List.of(Unit.TEASPOON, Unit.TABLESPOON, Unit.CUP, Unit.OUNCE, Unit.POUND, Unit.GRAM, Unit.KILOGRAM, Unit.MILLILITER, Unit.LITER, Unit.PINCH, Unit.DASH, Unit.EACH, Unit.NUMBER));
    ;
    private String description;
    private final List<Unit> units;
    IngredientState(List<Unit> units){
        if (this.name().equals("OTHER")){
            throw new IllegalArgumentException("Invalid MatterState: Please provide a description for this state.");
        }
        this.description = this.name();
        this.units = units;
    }

    public void setOtherDescription(String description){
        if (this.name().equals("OTHER")){
            this.description = description;
        }
    }
}
