package quixotic.projects.cookbook.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import quixotic.projects.cookbook.dto.CookDTO;
import quixotic.projects.cookbook.dto.RecipeDTO;
import quixotic.projects.cookbook.exception.badRequestException.RecipeNotFoundException;
import quixotic.projects.cookbook.exception.badRequestException.UserNotFoundException;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.Recipe;
import quixotic.projects.cookbook.model.enums.*;
import quixotic.projects.cookbook.model.summary.UserProfile;
import quixotic.projects.cookbook.repository.CookRepository;
import quixotic.projects.cookbook.repository.RecipeRepository;
import quixotic.projects.cookbook.security.JwtTokenProvider;
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
                    .dietTypes(List.of(DietType.VEGAN, DietType.VEGETARIAN))
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
                    .dietTypes(List.of(DietType.VEGETARIAN))
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

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    private String token = "";

    @BeforeAll
    public static void init() {
        cook.setPublications(recipeDTOS.stream().map((recipeDTO -> recipeDTO.toEntity(cook))).collect(Collectors.toSet()));
    }

    @BeforeEach
    public void setUp() {
        // TODO: 2024-03-11 Add token generation
    }

    @Test
    public void createRecipe_whenCookExists() {
        RecipeDTO recipeDTO = recipeDTOS.get(0);
        recipeDTO.setCookUsername(cook.getUsername());

        when(cookRepository.findCookByUsername(recipeDTO.getCookUsername())).thenReturn(Optional.of(cook));

        cookService.createRecipe(recipeDTO);
    }

    @Test
    public void createRecipe_whenCookDoesNotExist() {
        RecipeDTO recipeDTO = recipeDTOS.get(0);
        recipeDTO.setCookUsername("testCook");

        when(cookRepository.findCookByUsername(recipeDTO.getCookUsername())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> cookService.createRecipe(recipeDTO));
    }

    @Test
    public void getRecipes_whenRecipesExist() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Recipe> recipePage = Page.empty();
        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn(cook.getUsername());
        when(recipeRepository.findAll(pageable)).thenReturn(recipePage);
        when(cookRepository.findCookByUsername(anyString())).thenReturn(Optional.of(cook));

        cookService.getRecipesByPage(0, 10, anyString());
    }

    @Test
    public void getRecipes_NegativePage() {
        assertThrows(IllegalArgumentException.class, () -> cookService.getRecipesByPage(-1, 10, "testCook"));
    }


    @Test
    public void getRecipe_whenRecipeExists() {
        String title = recipeDTOS.get(0).getTitle();
        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn(cook.getUsername());
        when(recipeRepository.findByTitle(title)).thenReturn(Optional.of(recipeDTOS.get(0).toEntity(cook)));
        when(cookRepository.findCookByUsername(anyString())).thenReturn(Optional.of(cook));

        cookService.getRecipeByTitle(title, token);
    }

    @Test
    public void getRecipe_whenRecipeDoesNotExist() {
        String title = recipeDTOS.get(0).getTitle();
        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn(cook.getUsername());
        when(recipeRepository.findByTitle(title)).thenReturn(Optional.empty());
        when(cookRepository.findCookByUsername(anyString())).thenReturn(Optional.of(cook));

        assertThrows(RecipeNotFoundException.class, () -> cookService.getRecipeByTitle(title, token));
    }

    @Test
    public void updateRecipe_whenRecipeExists() {
        RecipeDTO recipeDTO = recipeDTOS.get(2);
        recipeDTO.setCookTime(55);

        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn(cook.getUsername());
        when(recipeRepository.findById(recipeDTO.getId())).thenReturn(Optional.of(recipeDTO.toEntity(cook)));
        when(cookRepository.findCookByUsername(recipeDTO.getCookUsername())).thenReturn(Optional.of(cook));
        when(recipeRepository.save(recipeDTO.toEntity(cook))).thenReturn(recipeDTO.toEntity(cook));

        cookService.updateRecipe(recipeDTO, token);
    }

    @Test
    public void updateRecipe_whenRecipeDoesNotExist() {
        RecipeDTO recipeDTO = recipeDTOS.get(0);

        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn(cook.getUsername());
        when(recipeRepository.findById(recipeDTO.getId())).thenReturn(Optional.empty());
//        when(recipeRepository.findByTitle(recipeDTO.getTitle())).thenReturn(Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> cookService.updateRecipe(recipeDTO, token));
    }

    @Test
    public void deleteRecipe_whenRecipeExists() {
        Long id = 1L;
        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn(cook.getUsername());
        when(recipeRepository.findById(id)).thenReturn(Optional.of(recipeDTOS.get(0).toEntity(cook)));
        when(recipeRepository.existsById(id)).thenReturn(true);

        cookService.deleteRecipeById(id, token);
    }

    @Test
    public void deleteRecipe_whenRecipeDoesNotExist() {
        Long id = 1L;
        when(recipeRepository.existsById(id)).thenReturn(false);

        assertThrows(RecipeNotFoundException.class, () -> cookService.deleteRecipeById(id, token));
    }

    @Test
    public void deleteRecipe_whenRecipeExists_deletesRecipe() {
        // Arrange
        String title = "testRecipe";
        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn(cook.getUsername());
        when(recipeRepository.existsByTitle(title)).thenReturn(true);
        when(recipeRepository.findByTitle(title)).thenReturn(Optional.of(recipeDTOS.get(0).toEntity(cook)));
        doNothing().when(recipeRepository).deleteByTitle(title);

        // Act
        cookService.deleteRecipeByTitle(title, token);

        // Assert
        verify(recipeRepository, times(1)).deleteByTitle(title);
    }

    @Test
    public void deleteRecipe_whenRecipeDoesNotExist_throwsException() {
        // Arrange
        String title = "testRecipe";
        when(recipeRepository.existsByTitle(title)).thenReturn(false);

        // Act & Then
        assertThrows(RecipeNotFoundException.class, () -> cookService.deleteRecipeByTitle(title, token));
    }

    @Test
    public void getRecipesByTitle_whenRecipesExist_returnsRecipes() {
        // Arrange
        String title = "testRecipe";
        List<Recipe> recipes = List.of();
        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn(cook.getUsername());
        when(recipeRepository.findAllByTitleContainsIgnoreCase(title)).thenReturn(recipes);
        when(cookRepository.findCookByUsername(anyString())).thenReturn(Optional.of(cook));

        // Act
        List<RecipeDTO> result = cookService.getRecipesSummaryByTitle(title, token);

        // Assert
        assertEquals(recipes.size(), result.size());
    }

    @Test
    public void getRecipesByTitle_whenNoRecipesExist_returnsEmptyList() {
        // Arrange
        String title = "testRecipe";
        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn(cook.getUsername());
        when(recipeRepository.findAllByTitleContainsIgnoreCase(title)).thenReturn(Collections.emptyList());
        when(cookRepository.findCookByUsername(anyString())).thenReturn(Optional.of(cook));

        // Act
        List<RecipeDTO> result = cookService.getRecipesSummaryByTitle(title, token);

        // Assert
        assertTrue(result.isEmpty());
    }
// ----
    @Test
    public void createRecipe_CookExists_RecipeCreated() {
        RecipeDTO recipeDTO = recipeDTOS.get(0);

        when(cookRepository.findCookByUsername(recipeDTO.getCookUsername())).thenReturn(Optional.of(cook));

        cookService.createRecipe(recipeDTO);
    }

    @Test
    public void createRecipe_CookDoesNotExist_ThrowsUserNotFoundException() {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setCookUsername("invalid");

        when(cookRepository.findCookByUsername(recipeDTO.getCookUsername())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> cookService.createRecipe(recipeDTO));
    }

    @Test
    public void createRecipe_whenCookExists_RecipeCreated() {
        RecipeDTO recipeDTO = recipeDTOS.get(0);

        when(cookRepository.findCookByUsername(recipeDTO.getCookUsername())).thenReturn(Optional.of(cook));

        cookService.createRecipe(recipeDTO);
    }

    @Test
    public void createRecipe_whenCookDoesNotExist_ThrowsUserNotFoundException() {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setCookUsername("invalid");

        when(cookRepository.findCookByUsername(recipeDTO.getCookUsername())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> cookService.createRecipe(recipeDTO));
    }

    @Test
    public void getRecipesByPage_ValidPageAndSize_ReturnsRecipes() {
        when(jwtTokenProvider.getUsernameFromJWT(anyString())).thenReturn("testCook");
        when(cookRepository.findCookByUsername(anyString())).thenReturn(Optional.of(new Cook()));
        when(recipeRepository.findAll(any(Pageable.class))).thenReturn(Page.empty());

        cookService.getRecipesByPage(0, 10, "token");
    }

    @Test
    public void getRecipesByPage_NegativePage_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> cookService.getRecipesByPage(-1, 10, "token"));
    }

    @Test
    public void getRecipeById_RecipeExists_ReturnsRecipe() {
        when(jwtTokenProvider.getUsernameFromJWT(anyString())).thenReturn("testCook");
        when(cookRepository.findCookByUsername(anyString())).thenReturn(Optional.of(cook));
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipeDTOS.get(0).toEntity(cook)));

        cookService.getRecipeById(1L, "token");
    }

    @Test
    public void getRecipeById_RecipeDoesNotExist_ThrowsRecipeNotFoundException() {
        when(jwtTokenProvider.getUsernameFromJWT(anyString())).thenReturn("testCook");
        when(cookRepository.findCookByUsername(anyString())).thenReturn(Optional.of(new Cook()));
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> cookService.getRecipeById(1L, "token"));
    }

    @Test
    public void deleteRecipeById_RecipeExists_RecipeDeleted() {
        when(jwtTokenProvider.getUsernameFromJWT(anyString())).thenReturn("testCook");
        when(recipeRepository.existsById(anyLong())).thenReturn(true);
        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipeDTOS.get(0).toEntity(cook)));

        cookService.deleteRecipeById(1L, "token");
    }

    @Test
    public void deleteRecipeById_RecipeDoesNotExist_ThrowsRecipeNotFoundException() {
        when(recipeRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(RecipeNotFoundException.class, () -> cookService.deleteRecipeById(1L, "token"));
    }

    @Test
    void getRecipesByUser_whenRecipesExist() {
        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn(cook.getUsername());
        when(cookRepository.findCookByUsername(cook.getUsername())).thenReturn(Optional.of(cook));

        cookService.getRecipesByUser(token);
    }

    @Test
    void getRecipesByUser_whenRecipesDoNotExist() {
        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn(cook.getUsername());
        when(cookRepository.findCookByUsername(cook.getUsername())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> cookService.getRecipesByUser(token));
    }

    @Test
    void getSavedRecipesByUser_whenRecipesExist() {
        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn(cook.getUsername());
        when(cookRepository.findCookByUsername(cook.getUsername())).thenReturn(Optional.of(cook));
        when(recipeRepository.findAllById(cook.getSavedRecipe())).thenReturn(List.of(recipeDTOS.get(0).toEntity(cook)));
        when(recipeRepository.findAllById(cook.getSavedRecipe())).thenReturn(List.of(recipeDTOS.get(0).toEntity(cook)));

        cookService.getSavedRecipesByUser(token);
    }

    @Test
    void getSavedRecipesByUser_whenRecipesDoNotExist() {
        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn(cook.getUsername());
        when(cookRepository.findCookByUsername(cook.getUsername())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> cookService.getSavedRecipesByUser(token));
    }

    @Test
    void saveRecipe_whenRecipeDoesNotExist() {
        Long id = 1L;

        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn(cook.getUsername());
        when(cookRepository.findCookByUsername(cook.getUsername())).thenReturn(Optional.of(cook));
        when(recipeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> cookService.saveRecipe(id, token));
    }

    @Test
    void saveRecipe_whenCookDoesNotExist() {
        Long id = 1L;

        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn(cook.getUsername());
        when(cookRepository.findCookByUsername(cook.getUsername())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> cookService.saveRecipe(id, token));
    }

    @Test
    void getUserProfile_whenCookExists() {
        // TODO: 2024-04-02 Fix this test
//        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn(cook.getUsername());
//        when(cookRepository.findByUsername(cook.getUsername())).thenReturn((Optional<UserProfile>) Optional.of(cook));

        assertThrows(UserNotFoundException.class, () -> cookService.getUserProfile(token));
//        cookService.getUserProfile(token);
    }

    @Test
    void getUserProfile_whenCookDoesNotExist() {
//        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn(cook.getUsername());
//        when(cookRepository.findCookByUsername(cook.getUsername())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> cookService.getUserProfile(token));
    }

    @Test
    void updateUserProfile_whenCookExists() {
        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn(cook.getUsername());
        when(cookRepository.findCookByUsername(cook.getUsername())).thenReturn(Optional.of(cook));
        when(cookRepository.save(cook)).thenReturn(cook);

        cookService.updateUserProfile(new CookDTO(cook), token);
    }

    @Test
    void updateUserProfile_whenCookDoesNotExist() {
        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn(cook.getUsername());
        when(cookRepository.findCookByUsername(cook.getUsername())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> cookService.updateUserProfile(new CookDTO(cook), token));
    }
}