package quixotic.projects.cookbook;

import jakarta.transaction.Transactional;
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
        cookService.createTrick(
                new TrickDTO(
                        Trick.builder()
                                .title("Une autre astuce")
                                .description("Ne pas mettre de sel dans l'eau pour faire bouillir des pâtes")
                                .visibility(Visibility.PUBLIC)
                                .cook(Cook.builder().username("TheChef").build())
                                .build()
                )
        );

    }

    private void createRecipes() {
        cookService.createRecipe(RecipeDTO.builder()
                .title("Foie Gras")
                .description("C'est le foie gras de ma marraine, donc la meilleure recette de foie gras")
                .cookUsername("TheCook")
                .visibility(Visibility.PUBLIC)
                .instructions(Set.of("Mettre le foie gras en puré à l'aide d'un couteau pour séparer les nerfs",
                        "Mettre le foie gras dans un plat. Le mieux est de faire en sorte que le foie gras soit bombé au centre",
                        "Ajouter du sel et du poivre et le porto sur le dessus et laisser mariner 24h",
                        "Rincer à l'eau froide pour retirer l'exès de sel",
                        "Mettre le foie gras dans un petit plat de céramique avec un couvercle. Placer le foie gras de manière à ce qu'il soit le plus compacter possible, donc bien tasser les coins et le fond",
                        "Créer une pate de sel pour bien celler le plat. La pate de sel ce fait avec la farine de l'eau et du sel",
                        "Dans un bain marie, mettre le plat de foie gras et cuire à 400°F pendant 10 à 15 minutes. Bien important de ne pas dépasser 20 minutes au four",
                        "Retirer grossièrement l'exès de gras avec une cuillère sans toucher au foie gras",
                        "Mettre un poid sur le dessus du foie gras pour bien le compacter et le laisser refroidir. Recommandation: Utiliser une petite cane",
                        "Une fois refroidi, retirer le poid et ajouter le gras excédentaire sur le dessus du foie gras",
                        "Mettre au frigo jusqu'au jour de la dégustation"
                ))
                .ingredients(Set.of(
                        IngredientDTO.builder()
                                .name("Foie Gras")
                                .quantity(1).ingredientState(IngredientState.COUNTABLE)
                                .unit(Unit.NUMBER)
                                .build(),
                        IngredientDTO.builder()
                                .name("Porto")
                                .quantity(2).ingredientState(IngredientState.LIQUID)
                                .unit(Unit.TEASPOON)
                                .build(),
                        IngredientDTO.builder()
                                .name("Farine")
                                .quantity(6).ingredientState(IngredientState.LIQUID)
                                .unit(Unit.TABLESPOON)
                                .build(),
                        IngredientDTO.builder()
                                .name("Eau")
                                .quantity(4).ingredientState(IngredientState.LIQUID)
                                .unit(Unit.TABLESPOON)
                                .build(),
                        IngredientDTO.builder()
                                .name("Sel")
                                .quantity(3).ingredientState(IngredientState.LIQUID)
                                .unit(Unit.TABLESPOON)
                                .build()
                ))
                .category(RecipeType.ENTREE)
                .serving(4)
                .prepTime(150)
                .cookTime(10)
                .difficulty(DifficultyLevel.MEDIUM)
                .portionSize(PortionSize.MEDIUM)
                .dietTypes(List.of(DietType.CARNIVORE, DietType.OTHER))
//                .image()
                .build());

        cookService.createRecipe(RecipeDTO.builder()
                .title("Saumure pour saumon")
                .description("La meilleure recette de saumure qui existe")
                .cookUsername("TheCook")
                .visibility(Visibility.PUBLIC)
                .instructions(Set.of("Mélanger tous les ingrédients ensemble",
                        "Mettre le une couche d'environ 1 pouce de la saumure dans le fond d'un plat avec un couvercle",
                        "Mettre le saumon avec le muscle vers le bas, donc la peau vers le haut",
                        "Mettre le reste de la saumure sur le dessus du saumon",
                        "Fermer le couvercle et mettre au frigo pour 24h",
                        "Retirer le saumon de la saumure et essuyer avec du papier brun ou rincer à l'eau froide. À ce point, vous pouvez manger le saumon ou le fumer pour ajouter de la saveur",
                        "Pour fumer: Faire un feu avec des copeaux de bois de pommier ou d'érable. Lorsque le feu est bien établi, mettre le saumon sur la grille et fumer pendant 1h30 à 2h"
                ))
                .ingredients(Set.of(
                        IngredientDTO.builder()
                                .name("Filet de saumon")
                                .quantity(1000).ingredientState(IngredientState.SOLID)
                                .unit(Unit.GRAM)
                                .build(),
                        IngredientDTO.builder()
                                .name("Gros Sel")
                                .quantity(3).ingredientState(IngredientState.SOLID)
                                .unit(Unit.KILOGRAM)
                                .build(),
                        IngredientDTO.builder()
                                .name("Cassonade")
                                .quantity(6).ingredientState(IngredientState.POWDER)
                                .unit(Unit.KILOGRAM)
                                .build(),
                        IngredientDTO.builder()
                                .name("Fines herbes au citron")
                                .quantity(1.5f).ingredientState(IngredientState.POWDER)
                                .unit(Unit.CUP)
                                .build(),
                        IngredientDTO.builder()
                                .name("Aneth séchées")
                                .quantity(1).ingredientState(IngredientState.POWDER)
                                .unit(Unit.CUP)
                                .build(),
                        IngredientDTO.builder()
                                .name("Poivre noir en grain moulu grossièrement")
                                .quantity(5).ingredientState(IngredientState.POWDER)
                                .unit(Unit.TABLESPOON)
                                .build(),
                        IngredientDTO.builder()
                                .name("Fenouil en grain moulu grossièrement")
                                .quantity(2).ingredientState(IngredientState.POWDER)
                                .unit(Unit.TABLESPOON)
                                .build(),
                        IngredientDTO.builder()
                                .name("Baie de genièvre en grain moulu grossièrement")
                                .quantity(2).ingredientState(IngredientState.POWDER)
                                .unit(Unit.TABLESPOON)
                                .build()
                ))
                .category(RecipeType.APPETIZER)
                .serving(4)
                .prepTime(20)
                .cookTime(0)
                .difficulty(DifficultyLevel.MEDIUM)
                .portionSize(PortionSize.LARGE)
                .dietTypes(List.of(DietType.CARNIVORE, DietType.OTHER))
//                .image()
                .build());

        cookService.createRecipe(RecipeDTO.builder()
                .title("Muffins à la citrouille")
                .description("La meilleure recette de muffins à la citrouille")
                .cookUsername("TheCook")
                .visibility(Visibility.PUBLIC)
                .instructions(Set.of(
                        "À REMPLIR",
                        "Mélanger tous les ingrédients ensemble"
                ))
                .ingredients(Set.of(
                        IngredientDTO.builder()
                                .name("Sucre")
                                .quantity(2).ingredientState(IngredientState.POWDER)
                                .unit(Unit.CUP)
                                .build(),
                        IngredientDTO.builder()
                                .name("Farine")
                                .quantity(3).ingredientState(IngredientState.POWDER)
                                .unit(Unit.CUP)
                                .build(),
                        IngredientDTO.builder()
                                .name("Poudre à pâte")
                                .quantity(2).ingredientState(IngredientState.POWDER)
                                .unit(Unit.TEASPOON)
                                .build(),
                        IngredientDTO.builder()
                                .name("bicarbonate de sodium")
                                .quantity(3).ingredientState(IngredientState.POWDER)
                                .unit(Unit.TEASPOON)
                                .build(),
                        IngredientDTO.builder()
                                .name("Canelle")
                                .quantity(3).ingredientState(IngredientState.POWDER)
                                .unit(Unit.TEASPOON)
                                .build(),
                        IngredientDTO.builder()
                                .name("Citrouille en purée")
                                .quantity(1.5f).ingredientState(IngredientState.LIQUID)
                                .unit(Unit.CUP)
                                .build(),
                        IngredientDTO.builder()
                                .name("Oeuf")
                                .quantity(4).ingredientState(IngredientState.COUNTABLE)
                                .unit(Unit.NUMBER)
                                .build(),
                        IngredientDTO.builder()
                                .name("Huile végétale")
                                .quantity(1.5f).ingredientState(IngredientState.LIQUID)
                                .unit(Unit.TEASPOON)
                                .build()
                        ))
                .category(RecipeType.DESSERT)
                .serving(12)
                .prepTime(30)
                .cookTime(60)
                .difficulty(DifficultyLevel.EASY)
                .portionSize(PortionSize.MEDIUM)
                .dietTypes(List.of(DietType.OTHER))
//                .image()
                .build());

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
                .image(getImage())
                .build());
    }

    private byte[] getImage() {
        return new byte[]{-1, -40, -1, -32, 0, 16, 74, 70, 73, 70, 0, 1, 2, 0, 0, 1, 0, 1, 0, 0, -1, -37, 0, 67, 0, 8, 6, 6, 7, 6, 5, 8, 7, 7, 7, 9, 9, 8, 10, 12, 20, 13, 12, 11, 11, 12, 25, 18, 19, 15, 20, 29, 26, 31, 30, 29, 26, 28, 28, 32, 36, 46, 39, 32, 34, 44, 35, 28, 28, 40, 55, 41, 44, 48, 49, 52, 52, 52, 31, 39, 57, 61, 56, 50, 60, 46, 51, 52, 50, -1, -37, 0, 67, 1, 9, 9, 9, 12, 11, 12, 24, 13, 13, 24, 50, 33, 28, 33, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, -1, -64, 0, 17, 8, 2, 0, 2, 0, 3, 1, 34, 0, 2, 17, 1, 3, 17, 1, -1, -60, 0, 31, 0, 0, 1, 5, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, -1, -60, 0, -75, 16, 0, 2, 1, 3, 3, 2, 4, 3, 5, 5, 4, 4, 0, 0, 1, 125, 1, 2, 3, 0, 4, 17, 5, 18, 33, 49, 65, 6, 19, 81, 97, 7, 34, 113, 20, 50, -127, -111, -95, 8, 35, 66, -79, -63, 21, 82, -47, -16, 36, 51, 98, 114, -126, 9, 10, 22, 23, 24, 25, 26, 37, 38, 39, 40, 41, 42, 52, 53, 54, 55, 56, 57, 58, 67, 68, 69, 70, 71, 72, 73, 74, 83, 84, 85, 86, 87, 88, 89, 90, 99, 100, 101, 102, 103, 104, 105, 106, 115, 116, 117, 118, 119, 120, 121, 122, -125, -124, -123, -122, -121, -120, -119, -118, -110, -109, -108, -107, -106, -105, -104, -103, -102, -94, -93, -92, -91, -90, -89, -88, -87, -86, -78, -77, -76, -75, -74, -73, -72, -71, -70, -62, -61, -60, -59, -58, -57, -56, -55, -54, -46, -45, -44, -43, -42, -41, -40, -39, -38, -31, -30, -29, -28, -27, -26, -25, -24, -23, -22, -15, -14, -13, -12, -11, -10, -9, -8, -7, -6, -1, -60, 0, 31, 1, 0, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, -1, -60, 0, -75, 17, 0, 2, 1, 2, 4, 4, 3, 4, 7, 5, 4, 4, 0, 1, 2, 119, 0, 1, 2, 3, 17, 4, 5, 33, 49, 6, 18, 65, 81, 7, 97, 113, 19, 34, 50, -127, 8, 20, 66, -111, -95, -79, -63, 9, 35, 51, 82, -16, 21, 98, 114, -47, 10, 22, 36, 52, -31, 37, -15, 23, 24, 25, 26, 38, 39, 40, 41, 42, 53, 54, 55, 56, 57, 58, 67, 68, 69, 70, 71, 72, 73, 74, 83, 84, 85, 86, 87, 88, 89, 90, 99, 100, 101, 102, 103, 104, 105, 106, 115, 116, 117, 118, 119, 120, 121, 122, -126, -125, -124, -123, -122, -121, -120, -119, -118, -110, -109, -108, -107, -106, -105, -104, -103, -102, -94, -93, -92, -91, -90, -89, -88, -87, -86, -78, -77, -76, -75, -74, -73, -72, -71, -70, -62, -61, -60, -59, -58, -57, -56, -55, -54, -46, -45, -44, -43, -42, -41, -40, -39, -38, -30, -29, -28, -27, -26, -25, -24, -23, -22, -14, -13, -12, -11, -10, -9, -8, -7, -6, -1, -38, 0, 12, 3, 1, 0, 2, 17, 3, 17, 0, 63, 0, -9, -6, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 10, 40, -94, -128, 63, -1, -39};
    }
}
