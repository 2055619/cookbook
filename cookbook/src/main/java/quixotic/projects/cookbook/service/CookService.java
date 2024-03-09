package quixotic.projects.cookbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import quixotic.projects.cookbook.dto.IngredientDTO;
import quixotic.projects.cookbook.dto.RecipeDTO;
import quixotic.projects.cookbook.exception.badRequestException.RecipeNotFoundException;
import quixotic.projects.cookbook.exception.badRequestException.UserNotFoundException;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.Ingredient;
import quixotic.projects.cookbook.model.Recipe;
import quixotic.projects.cookbook.model.summary.RecipeSummary;
import quixotic.projects.cookbook.model.summary.UserProfile;
import quixotic.projects.cookbook.repository.CookRepository;
import quixotic.projects.cookbook.repository.RecipeRepository;
import quixotic.projects.cookbook.security.JwtTokenProvider;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CookService {
    private final CookRepository cookRepository;
    private final RecipeRepository recipeRepository;
    private final JwtTokenProvider jwtTokenProvider;

    //    Recipes
    public RecipeDTO createRecipe(RecipeDTO recipeDTO){
        Cook cook = cookRepository.findCookByUsername(recipeDTO.getCookUsername()).orElseThrow(UserNotFoundException::new);
        Recipe recipe = recipeDTO.toEntity(cook);

        recipe.getIngredients().forEach(ingredient -> ingredient.setRecipe(recipe));
        cook.addPublication(recipe);

        cookRepository.save(cook);
        return new RecipeDTO(recipe);
    }

    public List<RecipeDTO> getRecipes() {
        return recipeRepository.findAll().stream().map(RecipeDTO::new).toList();
    }
    public List<RecipeDTO> getRecipesByPage(int page, int size, String token) {
        if (page < 0 || size < 0)
            throw new IllegalArgumentException("Page and size must be greater than 0");
        String username = jwtTokenProvider.getUsernameFromJWT(token);

        Cook user = cookRepository.findCookByUsername(username).orElseThrow(UserNotFoundException::new);
        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> recipePage = recipeRepository.findAll(pageable);

        return recipePage.stream()
                .filter(recipe -> switch (recipe.getVisibility()) {
                    case PUBLIC -> true;
                    case FOLLOWERS -> user.getFollowers().contains(recipe.getCook());
                    case FRIENDS -> user.getFriends().contains(recipe.getCook());
                    case SECRET -> user.equals(recipe.getCook());
                })
                .map(RecipeDTO::new)
                .collect(Collectors.toList());
    }

    public RecipeDTO getRecipeById(Long id) {
        return new RecipeDTO(recipeRepository.findById(id).orElseThrow(RecipeNotFoundException::new));
    }

    public RecipeDTO getRecipeByTitle(String title) {
        return new RecipeDTO(recipeRepository.findByTitle(title).orElseThrow(RecipeNotFoundException::new));
    }

    public RecipeDTO updateRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = recipeRepository.findById(recipeDTO.getId())
                .orElseThrow(RecipeNotFoundException::new);

        recipe.setTitle(recipeDTO.getTitle());
        recipe.setDescription(recipeDTO.getDescription());
        recipe.setVisibility(recipeDTO.getVisibility());
        recipe.setCook(cookRepository.findCookByUsername(recipeDTO.getCookUsername()).orElseThrow(UserNotFoundException::new));
        recipe.setInstructions(recipeDTO.getInstructions());
        recipe.setIngredients(recipeDTO.getIngredients().stream().map(IngredientDTO::toEntity).collect(Collectors.toSet()));
        recipe.setCategory(recipeDTO.getCategory());
        recipe.setDifficulty(recipeDTO.getDifficulty());
        recipe.setServing(recipeDTO.getServing());
        recipe.setPortionSize(recipeDTO.getPortionSize());
        recipe.setDietTypes(recipeDTO.getDietTypes());
        recipe.setPrepTime(recipeDTO.getPrepTime());
        recipe.setCookTime(recipeDTO.getCookTime());

        for (Ingredient ingredient: recipe.getIngredients()){
            ingredient.setRecipe(recipe);
        }

//        System.out.println("Recipe: " + recipe);

        return new RecipeDTO(recipeRepository.save(recipe));
    }

    public void deleteRecipeById(Long id) {
        if (recipeRepository.existsById(id))
            recipeRepository.deleteById(id);
        else throw new RecipeNotFoundException();
    }
    public void deleteRecipeByTitle(String title) {
        if (recipeRepository.existsByTitle(title))
            recipeRepository.deleteByTitle(title);
        else throw new RecipeNotFoundException();
    }

    public List<RecipeSummary> getRecipesSummaryByTitle(String title) {
        return recipeRepository.findAllByTitleContainsIgnoreCase(title);
    }

    public List<RecipeDTO> getRecipesByUser(String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        return recipeRepository.findAllByCookUsername(username).stream().map(RecipeDTO::new).toList();
    }

    public UserProfile getUserProfile(String username) {
        return cookRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }
}
