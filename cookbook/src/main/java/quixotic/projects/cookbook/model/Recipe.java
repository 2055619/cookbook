package quixotic.projects.cookbook.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cookbook.enums.RecipeType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recipe {
    private String name;
    private String description;
    private String instructions;
    private String ingredients;
    private RecipeType category;
    private String prepTime;
    private String cookTime;
    private String servings;
    private String notes;
    private String difficulty;
    private String author;
    private LocalDate dateAdded;
    private LocalDate dateUpdated;
    private String image;
    private String rating;

}
