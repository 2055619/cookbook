package quixotic.projects.cookbook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cookbook.enums.Unit;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingrediant {
    private String name;
    private float amount;
    private Unit unit;
}
