package quixotic.projects.cookbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import quixotic.projects.cookbook.dto.IngredientDTO;
import quixotic.projects.cookbook.dto.RecipeDTO;
import quixotic.projects.cookbook.exception.badRequestException.RecipeNotFoundException;
import quixotic.projects.cookbook.exception.goneRequestException.UserNotFoundException;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.Recipe;
import quixotic.projects.cookbook.model.summary.RecipeSummary;
import quixotic.projects.cookbook.repository.CookRepository;
import quixotic.projects.cookbook.repository.RecipeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CookService {
    private final CookRepository cookRepository;
    private final RecipeRepository recipeRepository;

//    Recipes
    public RecipeDTO createRecipe(RecipeDTO recipeDTO){
        Cook cook = cookRepository.findByUsername(recipeDTO.getCookUsername()).orElseThrow(UserNotFoundException::new);
        Recipe recipe = recipeDTO.toEntity(cook);

        recipe.getIngredients().forEach(ingredient -> ingredient.setRecipe(recipe));
        cook.addPublication(recipe);

        cookRepository.save(cook);
        return new RecipeDTO(recipe);
//        return new RecipeDTO(recipeRepository.save(recipe));
    }

    public List<RecipeDTO> getRecipes() {
        return recipeRepository.findAll().stream().map(RecipeDTO::new).toList();
    }
    public List<RecipeDTO> getRecipes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> recipePage = recipeRepository.findAll(pageable);
        return recipePage.map(RecipeDTO::new).stream().toList();
    }
    public RecipeDTO getRecipe(String title) {
        return new RecipeDTO(recipeRepository.findByTitle(title).orElseThrow(RecipeNotFoundException::new));
    }

    public RecipeDTO updateRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = recipeRepository.findByTitle(recipeDTO.getTitle())
                .orElseThrow(RecipeNotFoundException::new);

        recipe.setTitle(recipeDTO.getTitle());
        recipe.setDescription(recipeDTO.getDescription());
        recipe.setVisibility(recipeDTO.getVisibility());
        recipe.setCook(cookRepository.findByUsername(recipeDTO.getCookUsername()).orElseThrow(UserNotFoundException::new));
        recipe.setInstructions(recipeDTO.getInstructions());
        recipe.setIngredients(recipeDTO.getIngredients().stream().map(IngredientDTO::toEntity).collect(Collectors.toSet()));
        recipe.setCategory(recipeDTO.getCategory());
        recipe.setDifficulty(recipeDTO.getDifficulty());
        recipe.setServing(recipeDTO.getServing());
        recipe.setPortionSize(recipeDTO.getPortionSize());
        recipe.setDietTypes(recipeDTO.getDietTypes());
        recipe.setPrepTime(recipeDTO.getPrepTime());
        recipe.setCookTime(recipeDTO.getCookTime());

        System.out.println("Recipe: " + recipe);

        return new RecipeDTO(recipeRepository.save(recipe));
    }

    public void deleteRecipe(Long id) {
        if (recipeRepository.existsById(id))
            recipeRepository.deleteById(id);
        else throw new RecipeNotFoundException();
    }
    public void deleteRecipe(String title) {
        if (recipeRepository.existsByTitle(title))
            recipeRepository.deleteByTitle(title);
        else throw new RecipeNotFoundException();
    }

    public List<RecipeSummary> getRecipesByTitle(String title) {
        return recipeRepository.findAllByTitleContainsIgnoreCase(title);
    }
}
