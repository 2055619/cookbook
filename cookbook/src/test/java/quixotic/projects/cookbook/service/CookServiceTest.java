package quixotic.projects.cookbook.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import quixotic.projects.cookbook.dto.RecipeDTO;
import quixotic.projects.cookbook.exception.badRequestException.RecipeNotFoundException;
import quixotic.projects.cookbook.exception.badRequestException.UserNotFoundException;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.Recipe;
import quixotic.projects.cookbook.model.enums.*;
import quixotic.projects.cookbook.model.summary.RecipeSummary;
import quixotic.projects.cookbook.repository.CookRepository;
import quixotic.projects.cookbook.repository.RecipeRepository;
import quixotic.projects.cookbook.security.Role;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CookServiceTest {
    private static final Cook cook = Cook.builder()
            .id(1L)
            .username("testCook")
            .email("asd@asd.com")
            .password("Nonne123!")
            .publications(new HashSet<>())
            .firstName("BlaBla")
            .lastName("BlaBlaLast")
            .powderUnit(Unit.GRAM)
            .liquidUnit(Unit.LITER)
            .solidUnit(Unit.KILOGRAM)
            .otherUnit(Unit.CUP)
            .role(Role.COOK)
            .build();
    private static final List<RecipeDTO> recipeDTOS = new ArrayList<>(List.of(
            RecipeDTO.builder()
                    .title("Chocolate Cake")
                    .description("A delicious and rich chocolate cake.")
                    .visibility(Visibility.PUBLIC)
                    .instructions(Set.of("Mix ingredients", "Bake in oven", "Let it cool", "Serve with cream"))
                    .ingredients(new HashSet<>()) // Add your ingredients here
                    .category(RecipeType.DESSERT)
                    .difficulty(DifficultyLevel.MEDIUM)
                    .serving(8)
                    .portionSize(PortionSize.LARGE)
                    .dietTypes(List.of(DietType.VEGETARIAN))
                    .prepTime(20)
                    .cookTime(60)
                    .cookUsername(cook.getUsername())
                    .build(),
            RecipeDTO.builder()
                    .title("Vegan Salad")
                    .description("A fresh and healthy vegan salad.")
                    .visibility(Visibility.PUBLIC)
                    .instructions(Set.of("Chop vegetables", "Mix in a bowl", "Serve with dressing"))
                    .ingredients(new HashSet<>()) // Add your ingredients here
                    .category(RecipeType.SALAD)
                    .difficulty(DifficultyLevel.EASY)
                    .serving(2)
                    .portionSize(PortionSize.SMALL)
                    .dietTypes(List.of(DietType.VEGAN, DietType.GLUTEN_FREE))
                    .prepTime(10)
                    .cookTime(100)
                    .cookUsername(cook.getUsername())
                    .build(),
            RecipeDTO.builder()
                    .title("Chicken Soup")
                    .description("A warm and hearty chicken soup.")
                    .visibility(Visibility.PUBLIC)
                    .instructions(Set.of("Boil chicken", "Add vegetables", "Simmer for 2 hours"))
                    .ingredients(new HashSet<>()) // Add your ingredients here
                    .category(RecipeType.SOUP)
                    .difficulty(DifficultyLevel.EASY)
                    .serving(4)
                    .portionSize(PortionSize.MEDIUM)
                    .dietTypes(List.of(DietType.GLUTEN_FREE))
                    .prepTime(15)
                    .cookTime(30)
                    .cookUsername(cook.getUsername())
                    .build()
    ));
    @InjectMocks
    private CookService cookService;
    @Mock
    private CookRepository cookRepository;
    @Mock
    private RecipeRepository recipeRepository;

    @BeforeAll
    public static void init() {
        cook.setPublications(recipeDTOS.stream().map((recipeDTO -> recipeDTO.toEntity(cook))).collect(Collectors.toSet()));
    }

    @Test
    public void createRecipe_whenCookExists() {
        RecipeDTO recipeDTO = recipeDTOS.get(0);
        recipeDTO.setCookUsername(cook.getUsername());

        when(cookRepository.findByUsername(recipeDTO.getCookUsername())).thenReturn(Optional.of(cook));

        cookService.createRecipe(recipeDTO);
    }

    @Test
    public void createRecipe_whenCookDoesNotExist() {
        RecipeDTO recipeDTO = recipeDTOS.get(0);
        recipeDTO.setCookUsername("testCook");

        when(cookRepository.findByUsername(recipeDTO.getCookUsername())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> cookService.createRecipe(recipeDTO));
    }

    @Test
    public void getRecipes_whenRecipesExist() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Recipe> recipePage = Page.empty();
        when(recipeRepository.findAll(pageable)).thenReturn(recipePage);

        cookService.getRecipes(0, 10);
    }

    @Test
    public void getRecipes_NegativePage() {
        assertThrows(IllegalArgumentException.class, () -> cookService.getRecipes(-1, 10));
    }


    @Test
    public void getRecipe_whenRecipeExists() {
        String title = recipeDTOS.get(0).getTitle();
        when(recipeRepository.findByTitle(title)).thenReturn(Optional.of(recipeDTOS.get(0).toEntity(cook)));

        cookService.getRecipe(title);
    }

    @Test
    public void getRecipe_whenRecipeDoesNotExist() {
        String title = recipeDTOS.get(0).getTitle();
        when(recipeRepository.findByTitle(title)).thenReturn(Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> cookService.getRecipe(title));
    }

    @Test
    public void updateRecipe_whenRecipeExists() {
        RecipeDTO recipeDTO = recipeDTOS.get(2);
        recipeDTO.setCookTime(55);

        when(recipeRepository.findByTitle(recipeDTO.getTitle())).thenReturn(Optional.of(recipeDTOS.get(2).toEntity(cook)));
        when(cookRepository.findByUsername(recipeDTO.getCookUsername())).thenReturn(Optional.of(cook));
        when(recipeRepository.save(recipeDTO.toEntity(cook))).thenReturn(recipeDTO.toEntity(cook));

        cookService.updateRecipe(recipeDTO);
    }

    @Test
    public void updateRecipe_whenRecipeDoesNotExist() {
        RecipeDTO recipeDTO = recipeDTOS.get(0);
        recipeDTO.setTitle("testRecipe");

        when(recipeRepository.findByTitle(recipeDTO.getTitle())).thenReturn(Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> cookService.updateRecipe(recipeDTO));
    }

    @Test
    public void deleteRecipe_whenRecipeExists() {
        Long id = 1L;
        when(recipeRepository.existsById(id)).thenReturn(true);

        cookService.deleteRecipe(id);
    }

    @Test
    public void deleteRecipe_whenRecipeDoesNotExist() {
        Long id = 1L;
        when(recipeRepository.existsById(id)).thenReturn(false);

        assertThrows(RecipeNotFoundException.class, () -> cookService.deleteRecipe(id));
    }

    @Test
    public void getRecipes_returnsAllRecipes() {
        // Arrange
        when(recipeRepository.findAll()).thenReturn(recipeDTOS.stream()
                .map((recipeDTO -> recipeDTO.toEntity(cook))).collect(Collectors.toList()));

        // Act
        List<RecipeDTO> result = cookService.getRecipes();

        // Assert
        assertEquals(recipeDTOS.size(), result.size());
    }

    @Test
    public void deleteRecipe_whenRecipeExists_deletesRecipe() {
        // Arrange
        String title = "testRecipe";
        when(recipeRepository.existsByTitle(title)).thenReturn(true);
        doNothing().when(recipeRepository).deleteByTitle(title);

        // Act
        cookService.deleteRecipe(title);

        // Assert
        verify(recipeRepository, times(1)).deleteByTitle(title);
    }

    @Test
    public void deleteRecipe_whenRecipeDoesNotExist_throwsException() {
        // Arrange
        String title = "testRecipe";
        when(recipeRepository.existsByTitle(title)).thenReturn(false);

        // Act & Then
        assertThrows(RecipeNotFoundException.class, () -> cookService.deleteRecipe(title));
    }

    @Test
    public void getRecipesByTitle_whenRecipesExist_returnsRecipes() {
        // Arrange
        String title = "testRecipe";
        List<RecipeSummary> recipes = List.of();
        when(recipeRepository.findAllByTitleContainsIgnoreCase(title)).thenReturn(recipes);

        // Act
        List<RecipeSummary> result = cookService.getRecipesByTitle(title);

        // Assert
        assertEquals(recipes.size(), result.size());
    }

    @Test
    public void getRecipesByTitle_whenNoRecipesExist_returnsEmptyList() {
        // Arrange
        String title = "testRecipe";
        when(recipeRepository.findAllByTitleContainsIgnoreCase(title)).thenReturn(Collections.emptyList());

        // Act
        List<RecipeSummary> result = cookService.getRecipesByTitle(title);

        // Assert
        assertTrue(result.isEmpty());
    }
}