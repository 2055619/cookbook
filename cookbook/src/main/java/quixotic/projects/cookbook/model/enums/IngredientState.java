package quixotic.projects.cookbook.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum IngredientState {
    POWDER,
    LIQUID,
    SOLID,
    COUNTABLE,
    OTHER("Other")
    ;
    private String description;
    IngredientState(){
        if (this.name().equals("OTHER")){
            throw new IllegalArgumentException("Invalid MatterState: Please provide a description for this state.");
        }
        this.description = this.name();
    }

    public void setOtherDescription(String description){
        if (this.name().equals("OTHER")){
            this.description = description;
        }
    }
}
