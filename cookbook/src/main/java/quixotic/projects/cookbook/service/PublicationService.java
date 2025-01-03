package quixotic.projects.cookbook.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import quixotic.projects.cookbook.dto.IngredientDTO;
import quixotic.projects.cookbook.dto.PublicationDTO;
import quixotic.projects.cookbook.dto.RecipeDTO;
import quixotic.projects.cookbook.dto.TrickDTO;
import quixotic.projects.cookbook.exception.badRequestException.PublicationNotFoundException;
import quixotic.projects.cookbook.exception.badRequestException.RecipeNotFoundException;
import quixotic.projects.cookbook.exception.badRequestException.UserNotFoundException;
import quixotic.projects.cookbook.exception.badRequestException.WrongUserException;
import quixotic.projects.cookbook.model.*;
import quixotic.projects.cookbook.repository.*;
import quixotic.projects.cookbook.security.JwtTokenProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PublicationService {
    private final JwtTokenProvider jwtTokenProvider;
    private final CookRepository cookRepository;
    private final RecipeRepository recipeRepository;
    private final PublicationRepository publicationRepository;
    private final FollowerRepository followerRepository;

    //    Publications
    public List<PublicationDTO> getPublicationsByPage(int page, int size, String token) {
        if (page < 0 || size < 0)
            throw new IllegalArgumentException("Page and size must be greater than 0");
        String username = jwtTokenProvider.getUsernameFromJWT(token);

        Cook user = cookRepository.findCookByUsername(username).orElseThrow(UserNotFoundException::new);
        Pageable pageable = PageRequest.of(page, size);

        return filterPublicationsByVisibility(publicationRepository.findAll(pageable).getContent(), user);
    }

    public PublicationDTO getPublicationByTitle(String title, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        Cook user = cookRepository.findCookByUsername(username).orElseThrow(UserNotFoundException::new);

        return filterPublicationsByVisibility(new ArrayList<>(List.of(
                publicationRepository.findByTitle(title).orElseThrow(PublicationNotFoundException::new))), user)
                .stream().findFirst()
                .orElseThrow(PublicationNotFoundException::new);
    }

    @Transactional
    public void deletePublicationById(String token, Long id) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        Cook cook = cookRepository.findCookByUsername(username).orElseThrow(UserNotFoundException::new);
        Publication publication = publicationRepository.findById(id).orElseThrow(PublicationNotFoundException::new);

        if (!publication.getCook().equals(cook))
            throw new WrongUserException();

        cook.removePublication(publication);
        publicationRepository.delete(publication);
    }

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
        recipe.setImage(recipeDTO.getImage());

        for (Ingredient ingredient : recipe.getIngredients()) {
            ingredient.setRecipe(recipe);
        }

        return new RecipeDTO(recipeRepository.save(recipe));
    }

    public void deleteRecipeById(Long id, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        Cook cook = cookRepository.findCookByUsername(username).orElseThrow(UserNotFoundException::new);
        Publication publication = publicationRepository.findById(id).orElseThrow(PublicationNotFoundException::new);

        if (!publication.getCook().equals(cook))
            throw new WrongUserException();

        cook.removePublication(publication);
        publicationRepository.delete(publication);
    }

    public void deleteRecipeByTitle(String title, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);

        if (recipeRepository.existsByTitle(title) && recipeRepository.findByTitle(title).get().getCook().getUsername().equals(username)){
//            TODO: Remove user from recipe
            recipeRepository.deleteByTitle(title);
        }
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

    public List<RecipeDTO> getSavedRecipesByUser(String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        Cook user = cookRepository.findCookByUsername(username).orElseThrow(UserNotFoundException::new);

        List<Recipe> savedRecipes = recipeRepository.findAllById(user.getSavedRecipe());
        List<RecipeDTO> savedRecipeDTOs = filterRecipesByVisibility(savedRecipes, user);

        if (savedRecipeDTOs.size() != savedRecipes.size()) {
            user.setSavedRecipe(savedRecipeDTOs.stream().map(RecipeDTO::getId).collect(Collectors.toSet()));
            cookRepository.save(user);
        }
        return savedRecipeDTOs;
    }

    public RecipeDTO saveRecipe(Long id, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        Cook user = cookRepository.findCookByUsername(username).orElseThrow(UserNotFoundException::new);
        Recipe recipe = recipeRepository.findById(id).orElseThrow(RecipeNotFoundException::new);

        user.saveRecipe(recipe);
        cookRepository.save(user);
        return new RecipeDTO(recipe);
    }

    public void saveImage(String base64Image, Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("Recipe not found"));
        recipe.setImage(Base64.decodeBase64(base64Image));
        recipeRepository.save(recipe);
    }

    //    Tricks
    public TrickDTO createTrick(TrickDTO trickDTO) {
        Cook cook = cookRepository.findCookByUsername(trickDTO.getCookUsername()).orElseThrow(UserNotFoundException::new);
        Trick trick = trickDTO.toEntity(cook);

        cook.addPublication(trick);

        cookRepository.save(cook);
        return new TrickDTO(trick);
    }

    public PublicationDTO getTrickByTitle(String token, String title) {
        Cook cook = cookRepository.findCookByUsername(jwtTokenProvider.getUsernameFromJWT(token)).orElseThrow(UserNotFoundException::new);

        return filterPublicationsByVisibility(List.of(publicationRepository.findByTitle(title).orElseThrow(PublicationNotFoundException::new)), cook).get(0);
    }

    @Transactional
    public boolean deleteTrickById(String token, Long id) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        Cook cook = cookRepository.findCookByUsername(username).orElseThrow(UserNotFoundException::new);
        Trick trick = (Trick) publicationRepository.findById(id).orElseThrow(PublicationNotFoundException::new);

        if (!trick.getCook().equals(cook))
            throw new WrongUserException();

        cook.removePublication(trick);
        publicationRepository.delete(trick);
        return true;
    }

    public PublicationDTO updateTrick(TrickDTO trickDTO) {
        Trick trick = (Trick) publicationRepository.findById(trickDTO.getId()).orElseThrow(PublicationNotFoundException::new);

        trick.setTitle(trickDTO.getTitle());
        trick.setDescription(trickDTO.getDescription());
        trick.setVisibility(trickDTO.getVisibility());
        trick.setCook(cookRepository.findCookByUsername(trickDTO.getCookUsername()).orElseThrow(UserNotFoundException::new));

        return new TrickDTO(publicationRepository.save(trick));
    }

    private List<PublicationDTO> filterPublicationsByVisibility(List<Publication> publications, Cook user) {
        return publications.stream()
                .filter(publication -> switch (publication.getVisibility()) {
                    case PUBLIC -> true;
                    case FOLLOWERS -> user.equals(publication.getCook()) || followerRepository.findByFollowedAndFollower(publication.getCook(), user).isPresent();
                    case FRIENDS -> user.equals(publication.getCook()) || followerRepository.findByFollowedAndFollower(publication.getCook(), user).isPresent() &&
                            followerRepository.findByFollowedAndFollower(user, publication.getCook()).isPresent();
                    case SECRET -> user.equals(publication.getCook());
                })
                .map((publication -> switch (publication.getPublicationType()) {
                    case RECIPE -> {
                        if (publication instanceof Recipe) {
                            yield new RecipeDTO((Recipe) publication);
                        } else {
                            yield recipeRepository.findByTitle(publication.getTitle())
                                    .map(RecipeDTO::new)
                                    .orElseThrow(RecipeNotFoundException::new);

                        }
                    }
                    case TRICK -> {
                        if (publication instanceof Trick) {
                            yield new TrickDTO((Trick) publication);
                        }
                        yield new PublicationDTO(publication);
                    }
                })).collect(Collectors.toList());
    }

    private List<RecipeDTO> filterRecipesByVisibility(List<Recipe> recipes, Cook user) {
        return recipes.stream()
                .filter(recipe -> switch (recipe.getVisibility()) {
                    case PUBLIC -> true;
                    case FOLLOWERS -> user.equals(recipe.getCook()) || followerRepository.findByFollowedAndFollower(recipe.getCook(), user).isPresent();
                    case FRIENDS -> user.equals(recipe.getCook()) || followerRepository.findByFollowedAndFollower(recipe.getCook(), user).isPresent() &&
                            followerRepository.findByFollowedAndFollower(user, recipe.getCook()).isPresent();
                    case SECRET -> user.equals(recipe.getCook());
                })
                .map(RecipeDTO::new)
                .collect(Collectors.toList());
    }

}
