package quixotic.projects.cookbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import quixotic.projects.cookbook.dto.IngredientDTO;
import quixotic.projects.cookbook.dto.RecipeDTO;
import quixotic.projects.cookbook.dto.SignUpDTO;
import quixotic.projects.cookbook.dto.TrickDTO;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.Trick;
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
        createTricks();
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
                .username("TheCook")
                .password("Password123")
                .email("qwe@gmail.com")
                .firstName("Admin")
                .lastName("Admin")
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

    private void createTricks() {
        cookService.createTrick(
                new TrickDTO(
                        Trick.builder()
                                .title("This is it")
                                .description("Don't cook your steak in olive oil")
                                .visibility(Visibility.PUBLIC)
                                .cook(Cook.builder().username("TheCook").build())
                                .build()
                )
        );

    }

    private void createRecipes() {
        cookService.createRecipe(RecipeDTO.builder()
                .title("Pancakes")
                .description("The best pancakes you'll ever eat")
                .cookUsername("TheChef")
                .visibility(Visibility.PUBLIC)
                .instructions(Set.of("Mix the ingredients", "Cook the pancakes"))
                .ingredients(Set.of(
                        IngredientDTO.builder()
                                .name("Farine")
                                .quantity(2).ingredientState(IngredientState.LIQUID)
                                .unit(Unit.CUP)
                                .build(),
                        IngredientDTO.builder()
                                .name("Oeuf")
                                .quantity(2).ingredientState(IngredientState.COUNTABLE)
                                .unit(Unit.NUMBER)
                                .build(),
                        IngredientDTO.builder()
                                .name("Milk")
                                .quantity(1).ingredientState(IngredientState.LIQUID)
                                .unit(Unit.CUP)
                                .build()
                ))
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
                .ingredients(Set.of(
                        IngredientDTO.builder()
                                .name("Farine")
                                .quantity(2).ingredientState(IngredientState.LIQUID)
                                .unit(Unit.CUP)
                                .build(),
                        IngredientDTO.builder()
                                .name("Oeuf")
                                .quantity(2).ingredientState(IngredientState.COUNTABLE)
                                .unit(Unit.NUMBER)
                                .build(),
                        IngredientDTO.builder()
                                .name("Milk")
                                .quantity(1).ingredientState(IngredientState.LIQUID)
                                .unit(Unit.CUP)
                                .build()
                ))
                .category(RecipeType.BREAKFAST)
                .serving(4)
                .prepTime(10)
                .cookTime(10)
                .difficulty(DifficultyLevel.EASY)
                .portionSize(PortionSize.SMALL)
                .dietTypes(List.of(DietType.CARNIVORE))
                .build());

        cookService.createRecipe(RecipeDTO.builder()
                .title("Chicken Alfredo")
                .description("Creamy and delicious chicken alfredo")
                .cookUsername("TheChef")
                .visibility(Visibility.PUBLIC)
                .instructions(Set.of("Boil the pasta", "Cook the chicken", "Mix with Alfredo sauce"))
                .ingredients(Set.of(
                        IngredientDTO.builder()
                                .name("Pasta")
                                .quantity(200).ingredientState(IngredientState.SOLID)
                                .unit(Unit.GRAM)
                                .build(),
                        IngredientDTO.builder()
                                .name("Chicken")
                                .quantity(200).ingredientState(IngredientState.SOLID)
                                .unit(Unit.GRAM)
                                .build(),
                        IngredientDTO.builder()
                                .name("Alfredo Sauce")
                                .quantity(100).ingredientState(IngredientState.LIQUID)
                                .unit(Unit.MILLILITER)
                                .build()
                ))
                .category(RecipeType.MAIN)
                .serving(2)
                .prepTime(15)
                .cookTime(15)
                .difficulty(DifficultyLevel.MEDIUM)
                .portionSize(PortionSize.MEDIUM)
                .dietTypes(List.of(DietType.CARNIVORE))
                .build());

        cookService.createRecipe(RecipeDTO.builder()
                .title("Vegan Salad")
                .description("Healthy and fresh vegan salad")
                .cookUsername("TheCook")
                .visibility(Visibility.PUBLIC)
                .instructions(Set.of("Chop the vegetables", "Mix with dressing"))
                .ingredients(Set.of(
                        IngredientDTO.builder()
                                .name("Lettuce")
                                .quantity(100).ingredientState(IngredientState.SOLID)
                                .unit(Unit.GRAM)
                                .build(),
                        IngredientDTO.builder()
                                .name("Tomato")
                                .quantity(1).ingredientState(IngredientState.COUNTABLE)
                                .unit(Unit.NUMBER)
                                .build(),
                        IngredientDTO.builder()
                                .name("Cucumber")
                                .quantity(1).ingredientState(IngredientState.COUNTABLE)
                                .unit(Unit.NUMBER)
                                .build(),
                        IngredientDTO.builder()
                                .name("Vegan Dressing")
                                .quantity(50).ingredientState(IngredientState.LIQUID)
                                .unit(Unit.MILLILITER)
                                .build()
                ))
                .category(RecipeType.SALAD)
                .serving(1)
                .prepTime(10)
                .cookTime(0)
                .difficulty(DifficultyLevel.EASY)
                .portionSize(PortionSize.SMALL)
                .dietTypes(List.of(DietType.VEGAN))
                .build());
    }
}
