package quixotic.projects.cookbook.model;

import jakarta.persistence.*;
import lombok.*;
import quixotic.projects.cookbook.model.enums.DifficultyLevel;
import quixotic.projects.cookbook.model.enums.PortionType;
import quixotic.projects.cookbook.model.enums.RecipeType;

import java.util.HashSet;
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
    private float prepTime;
    private float cookTime;
    private int serving;
    @Enumerated(EnumType.STRING)
    private PortionType portionType;
}
