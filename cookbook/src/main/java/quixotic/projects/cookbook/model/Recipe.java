package quixotic.projects.cookbook.model;

import jakarta.persistence.*;
import lombok.*;
import quixotic.projects.cookbook.model.enums.DietType;
import quixotic.projects.cookbook.model.enums.DifficultyLevel;
import quixotic.projects.cookbook.model.enums.PortionSize;
import quixotic.projects.cookbook.model.enums.RecipeType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Recipe extends Publication {
    @ElementCollection
    private Set<String> instructions = new HashSet<>();
    @OneToMany(mappedBy = "recipe")
    private Set<Ingredient> ingredients;
    @Enumerated(EnumType.STRING)
    private RecipeType category;
    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficulty;
    @Enumerated(EnumType.STRING)
    private PortionSize portionSize;
    @Enumerated(EnumType.STRING)
    private List<DietType> dietTypes;
    private float prepTime;
    private float cookTime;
    private int serving;

}
