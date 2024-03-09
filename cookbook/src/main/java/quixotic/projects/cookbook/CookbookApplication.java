package quixotic.projects.cookbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import quixotic.projects.cookbook.dto.IngredientDTO;
import quixotic.projects.cookbook.dto.RecipeDTO;
import quixotic.projects.cookbook.dto.SignUpDTO;
import quixotic.projects.cookbook.model.enums.*;
import quixotic.projects.cookbook.service.CookService;
import quixotic.projects.cookbook.service.UserService;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class CookbookApplication implements CommandLineRunner {
    @Autowired
    private CookService cookService;
    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(CookbookApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        createCooks();
        createRecipes();
    }

    private void createRecipes() {
        cookService.createRecipe(RecipeDTO.builder()
                .title("Pancakes")
                .description("The best pancakes you'll ever eat")
                .cookUsername("TheChef")
                .visibility(Visibility.PUBLIC)
                .instructions(Set.of("Mix the ingredients", "Cook the pancakes"))
                .ingredients(Set.of(IngredientDTO.builder()
                        .name("Flour")
                        .quantity(2).ingredientState(IngredientState.LIQUID)
                        .unit(Unit.CUP)
                        .build()))
                .category(RecipeType.BREAKFAST)
                .serving(4)
                .prepTime(10)
                .cookTime(10)
                .difficulty(DifficultyLevel.EASY)
                .portionSize(PortionSize.SMALL)
                .dietTypes(List.of(DietType.CARNIVORE))
                .build());

        cookService.createRecipe(RecipeDTO.builder()
                .title("Pasta")
                .description("The best Pasta you'll ever eat")
                .cookUsername("testCook")
                .visibility(Visibility.SECRET)
                .instructions(Set.of("Mix the ingredients", "Cook the pancakes"))
                .ingredients(Set.of(IngredientDTO.builder()
                        .name("Flour")
                        .quantity(2).ingredientState(IngredientState.LIQUID)
                        .unit(Unit.CUP)
                        .build()))
                .category(RecipeType.BREAKFAST)
                .serving(4)
                .prepTime(10)
                .cookTime(10)
                .difficulty(DifficultyLevel.EASY)
                .portionSize(PortionSize.SMALL)
                .dietTypes(List.of(DietType.CARNIVORE))
                .build());

    }

    private void createCooks() {
        userService.createCook(SignUpDTO.builder()
                .username("TheChef")
                .password("Password123")
                .email("qwe@qwe.com")
                .firstName("John")
                .lastName("Doe")
                .powderUnit(Unit.CUP)
                .liquidUnit(Unit.CUP)
                .solidUnit(Unit.GRAM)
                .otherUnit(Unit.CUP)
                .build());
        userService.createCook(SignUpDTO.builder()
                .username("testCook")
                .email("asd@asd.com")
                .password("Nonne123!")
                .firstName("BlaBla")
                .lastName("BlaBlaLast")
                .powderUnit(Unit.GRAM)
                .liquidUnit(Unit.LITER)
                .solidUnit(Unit.KILOGRAM)
                .otherUnit(Unit.CUP)
                .build());
    }
}
