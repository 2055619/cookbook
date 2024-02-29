package quixotic.projects.cookbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quixotic.projects.cookbook.dto.RecipeDTO;
import quixotic.projects.cookbook.repository.CookRepository;
import quixotic.projects.cookbook.repository.RecipeRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CookService {
    private final CookRepository cookRepository;
    private final RecipeRepository recipeRepository;

//    Recipes
    public RecipeDTO createRecipe(RecipeDTO recipeDTO){
        return new RecipeDTO(recipeRepository.save(recipeDTO.toEntity()));
    }

    public List<RecipeDTO> getRecipes() {
        return recipeRepository.findAll().stream().map(RecipeDTO::new).toList();

    }

    public RecipeDTO getRecipe(Long id) {
        return new RecipeDTO(recipeRepository.findById(id).orElseThrow());
    }

    // TODO: 2024-02-28 Double check this method
    public RecipeDTO updateRecipe(RecipeDTO recipeDTO) {
        return new RecipeDTO(recipeRepository.save(recipeDTO.toEntity()));
    }

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }
}
