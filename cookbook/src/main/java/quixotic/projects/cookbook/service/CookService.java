package quixotic.projects.cookbook.service;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import quixotic.projects.cookbook.dto.*;
import quixotic.projects.cookbook.exception.badRequestException.PublicationNotFoundException;
import quixotic.projects.cookbook.exception.badRequestException.RecipeNotFoundException;
import quixotic.projects.cookbook.exception.badRequestException.UserNotFoundException;
import quixotic.projects.cookbook.exception.badRequestException.WrongUserException;
import quixotic.projects.cookbook.model.*;
import quixotic.projects.cookbook.model.enums.Unit;
import quixotic.projects.cookbook.model.summary.UserProfile;
import quixotic.projects.cookbook.repository.*;
import quixotic.projects.cookbook.security.JwtTokenProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CookService {
    private final JwtTokenProvider jwtTokenProvider;
    private final CookRepository cookRepository;
    private final RecipeRepository recipeRepository;
    private final PublicationRepository publicationRepository;
    private final ReactionRepository reactionRepository;
    private final CommentRepository commentRepository;


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

    //    Reaction
    public List<ReactionDTO> getReactionsByPublication(Long pubId) {
        Publication publication = publicationRepository.findById(pubId)
                .orElseThrow(PublicationNotFoundException::new);

        return reactionRepository.findAllByPublication(publication).stream()
                .map(ReactionDTO::new)
                .collect(Collectors.toList());
    }

    public ReactionDTO createReaction(ReactionDTO reactionDTO, String token) {
        Cook cook = cookRepository.findCookByUsername(jwtTokenProvider.getUsernameFromJWT(token))
                .orElseThrow(UserNotFoundException::new);
        if (!Objects.equals(cook.getUsername(), reactionDTO.getCookUsername()))
            throw new WrongUserException();
        Publication publication = publicationRepository.findById(reactionDTO.getPublicationId())
                .orElseThrow(PublicationNotFoundException::new);
//        TODO: Ajouter AvgRating
        List<Reaction> reactions = reactionRepository.findAllByPublication(publication);
        reactions.add(reactionDTO.toEntity(cook, publication));
        publication.calculateAvgRating(reactions);

        return new ReactionDTO(reactionRepository.save(reactionDTO.toEntity(cook, publication)));
    }

    //    User
    public UserProfile getUserProfile(String username) {
        return cookRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
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

    private List<PublicationDTO> filterPublicationsByVisibility(List<Publication> publications, Cook user) {
        return publications.stream()
                .filter(recipe -> switch (recipe.getVisibility()) {
                    case PUBLIC -> true;
                    case FOLLOWERS -> user.getFollowers().contains(recipe.getCook());
                    case FRIENDS -> user.getFriends().contains(recipe.getCook());
                    case SECRET -> user.equals(recipe.getCook());
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
                    case OTHER -> new PublicationDTO(publication);
                })).collect(Collectors.toList());
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

}
