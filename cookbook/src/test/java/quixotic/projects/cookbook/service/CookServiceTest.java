package quixotic.projects.cookbook.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import quixotic.projects.cookbook.dto.RecipeDTO;
import quixotic.projects.cookbook.exception.badRequestException.RecipeNotFoundException;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.Recipe;
import quixotic.projects.cookbook.repository.CookRepository;
import quixotic.projects.cookbook.repository.RecipeRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CookServiceTest {

    @InjectMocks
    private CookService cookService;

    @Mock
    private CookRepository cookRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createRecipe_whenCookExists() {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setCookUsername("testCook");

        Cook cook = new Cook();
        when(cookRepository.findByUsername(recipeDTO.getCookUsername())).thenReturn(Optional.of(cook));

        cookService.createRecipe(recipeDTO);
    }

    @Test
    public void createRecipe_whenCookDoesNotExist() {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setCookUsername("testCook");

        when(cookRepository.findByUsername(recipeDTO.getCookUsername())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> cookService.createRecipe(recipeDTO));
    }

    @Test
    public void getRecipes_whenRecipesExist() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Recipe> recipePage = Page.empty();
        when(recipeRepository.findAll(pageable)).thenReturn(recipePage);

        cookService.getRecipes(0, 10);
    }

    @Test
    public void getRecipe_whenRecipeExists() {
        String title = "testRecipe";
        when(recipeRepository.findByTitle(title)).thenReturn(Optional.of(new Recipe()));

        cookService.getRecipe(title);
    }

    @Test
    public void getRecipe_whenRecipeDoesNotExist() {
        String title = "testRecipe";
        when(recipeRepository.findByTitle(title)).thenReturn(Optional.empty());

        assertThrows(RecipeNotFoundException.class, () -> cookService.getRecipe(title));
    }

    @Test
    public void updateRecipe_whenRecipeExists() {
        RecipeDTO recipeDTO = new RecipeDTO();
        recipeDTO.setTitle("testRecipe");

        when(recipeRepository.findByTitle(recipeDTO.getTitle())).thenReturn(Optional.of(new Recipe()));

        cookService.updateRecipe(recipeDTO);
    }

    @Test
    public void updateRecipe_whenRecipeDoesNotExist() {
        RecipeDTO recipeDTO = new RecipeDTO();
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
}