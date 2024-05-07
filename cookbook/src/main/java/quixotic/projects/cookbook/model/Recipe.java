package quixotic.projects.cookbook.model;

import jakarta.persistence.*;
import lombok.*;
import quixotic.projects.cookbook.model.enums.*;

import java.util.*;

@Data
@NoArgsConstructor
@Entity
public class Recipe extends Publication {
    @ElementCollection
    @Column(length = 1000)
    private Set<String> instructions = new HashSet<>();
    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
    private byte[] image;

    @Builder
    public Recipe(String title, String description, Visibility visibility, Cook cook, Set<String> instructions, Set<Ingredient> ingredients, RecipeType category, DifficultyLevel difficulty, int serving, PortionSize portionSize, List<DietType> dietTypes, float prepTime, float cookTime, byte[] image) {
        super(title, description, visibility, cook, PublicationType.RECIPE);
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.category = category;
        this.difficulty = difficulty;
        this.serving = serving;
        this.portionSize = portionSize;
        this.dietTypes = dietTypes;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.image = image;
    }

    public Cook getCook() {
        return super.getCook();
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
    public void removeIngredient(Ingredient ingredient) {
        this.ingredients.remove(ingredient);
    }
    public void addInstruction(String instruction) {
        this.instructions.add(instruction);
    }
    public void removeInstruction(String instruction) {
        this.instructions.remove(instruction);
    }
    public void addDietType(DietType dietType) {
        this.dietTypes.add(dietType);
    }
    public void removeDietType(DietType dietType) {
        this.dietTypes.remove(dietType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
