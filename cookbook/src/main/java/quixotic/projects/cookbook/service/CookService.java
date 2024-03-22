package quixotic.projects.cookbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import quixotic.projects.cookbook.dto.CookDTO;
import quixotic.projects.cookbook.dto.IngredientDTO;
import quixotic.projects.cookbook.dto.RecipeDTO;
import quixotic.projects.cookbook.exception.badRequestException.RecipeNotFoundException;
import quixotic.projects.cookbook.exception.badRequestException.UserNotFoundException;
import quixotic.projects.cookbook.exception.badRequestException.WrongUserException;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.Ingredient;
import quixotic.projects.cookbook.model.Recipe;
import quixotic.projects.cookbook.model.enums.Unit;
import quixotic.projects.cookbook.model.summary.UserProfile;
import quixotic.projects.cookbook.repository.CookRepository;
import quixotic.projects.cookbook.repository.RecipeRepository;
import quixotic.projects.cookbook.security.JwtTokenProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CookService {
    private final CookRepository cookRepository;
    private final RecipeRepository recipeRepository;
    private final JwtTokenProvider jwtTokenProvider;

    //    Recipes
    public RecipeDTO createRecipe(RecipeDTO recipeDTO) {
        Cook cook = cookRepository.findCookByUsername(recipeDTO.getCookUsername()).orElseThrow(UserNotFoundException::new);
        Recipe recipe = recipeDTO.toEntity(cook);

        recipe.getIngredients().forEach(ingredient -> ingredient.setRecipe(recipe));
        cook.addPublication(recipe);

        cookRepository.save(cook);
        return new RecipeDTO(recipe);
    }

    public List<RecipeDTO> getRecipesByPage(int page, int size, String token) {
        if (page < 0 || size < 0)
            throw new IllegalArgumentException("Page and size must be greater than 0");
        String username = jwtTokenProvider.getUsernameFromJWT(token);

        Cook user = cookRepository.findCookByUsername(username).orElseThrow(UserNotFoundException::new);
        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> recipePage = recipeRepository.findAll(pageable);

        return filterRecipesByVisibility(recipePage.getContent(), user);
    }

    public RecipeDTO getRecipeById(Long id, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        Cook user = cookRepository.findCookByUsername(username).orElseThrow(UserNotFoundException::new);

        return filterRecipesByVisibility(new ArrayList<>(List.of(recipeRepository.findById(id)
                        .orElseThrow(RecipeNotFoundException::new))),
                user).stream().findFirst().orElseThrow(RecipeNotFoundException::new);
    }

    public RecipeDTO getRecipeByTitle(String title, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        Cook user = cookRepository.findCookByUsername(username).orElseThrow(UserNotFoundException::new);

        return filterRecipesByVisibility(new ArrayList<>(List.of(recipeRepository.findByTitle(title)
                .orElseThrow(RecipeNotFoundException::new))),
                user).stream().findFirst().orElseThrow(RecipeNotFoundException::new);
    }

    public RecipeDTO updateRecipe(RecipeDTO recipeDTO, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);

        Recipe recipe = recipeRepository.findById(recipeDTO.getId())
                .orElseThrow(RecipeNotFoundException::new);

        if (!recipe.getCook().getUsername().equals(username))
            throw new WrongUserException();

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

        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredient.setRecipe(recipe);
        }

        return new RecipeDTO(recipeRepository.save(recipe));
    }

    public void deleteRecipeById(Long id, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);

        if (recipeRepository.existsById(id) && recipeRepository.findById(id).get().getCook().getUsername().equals(username))
            recipeRepository.deleteById(id);
        else throw new RecipeNotFoundException();
    }

    public void deleteRecipeByTitle(String title, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);

        if (recipeRepository.existsByTitle(title) && recipeRepository.findByTitle(title).get().getCook().getUsername().equals(username))
            recipeRepository.deleteByTitle(title);
        else throw new RecipeNotFoundException();
    }

    public List<RecipeDTO> getRecipesSummaryByTitle(String title, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        Cook user = cookRepository.findCookByUsername(username).orElseThrow(UserNotFoundException::new);

        return filterRecipesByVisibility(recipeRepository.findAllByTitleContainsIgnoreCase(title), user);
    }

    public List<RecipeDTO> getRecipesByUser(String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        Cook cook = cookRepository.findCookByUsername(username).orElseThrow(UserNotFoundException::new);

        return recipeRepository.findRecipesByCook(cook).stream().map(RecipeDTO::new).toList();
    }

    public List<RecipeDTO> getLikedRecipesByUser(String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        Cook user = cookRepository.findCookByUsername(username).orElseThrow(UserNotFoundException::new);

        throw new UnsupportedOperationException("getLikedRecipesByUser() Not implemented yet");
//        return user.getLikedRecipes().stream().map(RecipeDTO::new).toList();
    }

    public RecipeDTO likeRecipe(Long id, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        Cook user = cookRepository.findCookByUsername(username).orElseThrow(UserNotFoundException::new);
        Recipe recipe = recipeRepository.findById(id).orElseThrow(RecipeNotFoundException::new);

        throw new UnsupportedOperationException("likeRecipe() Not implemented yet");

//        user.likeRecipe(recipe);
//        cookRepository.save(user);
//
//        return new RecipeDTO(recipe);
    }

    public UserProfile getUserProfile(String username) {
        return cookRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    private List<RecipeDTO> filterRecipesByVisibility(List<Recipe> recipes, Cook user) {
        return recipes.stream()
                .filter(recipe -> switch (recipe.getVisibility()) {
                    case PUBLIC -> true;
                    case FOLLOWERS -> user.getFollowers().contains(recipe.getCook());
                    case FRIENDS -> user.getFriends().contains(recipe.getCook());
                    case SECRET -> user.equals(recipe.getCook());
                })
                .map(RecipeDTO::new)
                .collect(Collectors.toList());
    }

    public CookDTO updateUserProfile(CookDTO cookDTO, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);

        Cook cook = cookRepository.findCookByUsername(username).orElseThrow(UserNotFoundException::new);

        cook.setEmail(cookDTO.getEmail());
        cook.setFirstName(cookDTO.getFirstName());
        cook.setLastName(cookDTO.getLastName());
        cook.setPowderUnit(Unit.valueOf(cookDTO.getPowderUnit()));
        cook.setLiquidUnit(Unit.valueOf(cookDTO.getLiquidUnit()));
        cook.setSolidUnit(Unit.valueOf(cookDTO.getSolidUnit()));
        cook.setOtherUnit(Unit.valueOf(cookDTO.getOtherUnit()));

        return new CookDTO(cookRepository.save(cook));
    }
}
