package quixotic.projects.cookbook.model;

import jakarta.persistence.*;
import lombok.*;
import quixotic.projects.cookbook.model.enums.RecipeType;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String instructions;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ingrediant> ingredients;
    private RecipeType category;
    private float prepTime;
    private float cookTime;
    private String notes;
    private String difficulty;
    private String author;
    private LocalDate dateAdded;
    private LocalDate dateUpdated;
    private String rating;

}
