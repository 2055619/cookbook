package quixotic.projects.cookbook.model;

import jakarta.persistence.*;
import lombok.*;
import quixotic.projects.cookbook.model.enums.DietType;
import quixotic.projects.cookbook.model.enums.DifficultyLevel;
import quixotic.projects.cookbook.model.enums.PortionSize;
import quixotic.projects.cookbook.model.enums.RecipeType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Recipe extends Publication {
    @Id
    @GeneratedValue
    private Long id;
    @ElementCollection
    private Set<String> instructions = new HashSet<>();
    @OneToMany(mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private RecipeType category;
    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficulty;
    private int serving;
    @Enumerated(EnumType.STRING)
    private PortionSize portionSize;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<DietType> dietTypes = new ArrayList<>();
    private float prepTime;
    private float cookTime;
}
