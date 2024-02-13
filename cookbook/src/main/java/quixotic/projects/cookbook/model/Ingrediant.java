package quixotic.projects.cookbook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cookbook.model.enums.Unit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ingrediant {
    private String name;
    private float amount;
    private Unit unit;
}
