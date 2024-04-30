package quixotic.projects.cookbook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import quixotic.projects.cookbook.dto.*;
import quixotic.projects.cookbook.exception.badRequestException.PublicationNotFoundException;
import quixotic.projects.cookbook.exception.badRequestException.RecipeNotFoundException;
import quixotic.projects.cookbook.exception.badRequestException.UserNotFoundException;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.enums.Unit;
import quixotic.projects.cookbook.model.summary.UserProfile;
import quixotic.projects.cookbook.repository.CookRepository;
import quixotic.projects.cookbook.security.JwtAuthenticationEntryPoint;
import quixotic.projects.cookbook.security.JwtTokenProvider;
import quixotic.projects.cookbook.security.Role;
import quixotic.projects.cookbook.security.SecurityConfiguration;
import quixotic.projects.cookbook.service.CookService;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig(classes = {CookController.class, CookbookExceptionHandler.class,
        SecurityConfiguration.class, JwtTokenProvider.class, JwtAuthenticationEntryPoint.class})
@WebMvcTest(CookController.class)
public class CookControllerTest {
    private final Cook cook = Cook.builder()
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

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CookRepository cookRepository;
    @MockBean
    private CookService cookService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private ObjectMapper objectMapper;
    private String token = "Tester";

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        when(cookRepository.findCookByUsername(anyString())).thenReturn(Optional.of(cook));
        this.token = jwtTokenProvider.generateToken(new UsernamePasswordAuthenticationToken("testCook", "Nonne123!"));
    }

    @Test
    public void getRecipesByTitle_ValidTitleProvided_returnsAccepted() throws Exception {
        List<RecipeDTO> recipeSummaries = new ArrayList<>();

        when(cookService.getRecipesSummaryByTitle(anyString(), anyString())).thenReturn(recipeSummaries);

        mockMvc.perform(get("/api/v1/cook/recipes/title")
                        .header("Authorization", token)
                        .param("title", "Test Recipe"))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getRecipes_ValidPageAndSizeProvided_returnsAccepted() throws Exception {
        List<RecipeDTO> recipeDTOS = new ArrayList<>();

        when(cookService.getRecipesByPage(0, 10, "testCook")).thenReturn(recipeDTOS);

        mockMvc.perform(get("/api/v1/cook/recipes")
                        .header("Authorization", token)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getRecipes_InvalidPageAndSizeProvided_returnsBadRequest() throws Exception {
        when(cookService.getRecipesByPage(-1, -1, token)).thenThrow(new IllegalArgumentException());
        mockMvc.perform(get("/api/v1/cook/recipes")
                        .header("Authorization", token)
                        .param("page", "-1")
                        .param("size", "-1"))
                .andExpect(status().is(611));
    }

    @Test
    public void getRecipe_ValidTitleProvided_returnsAccepted() throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO();

        when(cookService.getRecipeByTitle("Test Recipe", token)).thenReturn(recipeDTO);

        mockMvc.perform(get("/api/v1/cook/recipe/Test Recipe")
                        .header("Authorization", token))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getRecipeByTitle_InvalidTitleProvided_returnsBadRequest() throws Exception {
        doThrow(new RecipeNotFoundException()).when(cookService).getRecipeByTitle(anyString(), anyString());

        mockMvc.perform(get("/api/v1/cook/recipe/title")
                        .header("Authorization", token))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getRecipe_InvalidTitleProvided_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/cook/recipe/")
                        .header("Authorization", token))
                .andExpect(status().is(611));
    }

    @Test
    public void authenticateCook_ValidRecipeDTOProvided_returnsAccepted() throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO();

        when(cookService.createRecipe(recipeDTO)).thenReturn(recipeDTO);

        mockMvc.perform(post("/api/v1/cook/recipe")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipeDTO)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void authenticateCook_InvalidRecipeDTOProvided_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/cook/recipe")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().is(611));
    }

    @Test
    public void updateRecipe_ValidRecipeDTOProvided_returnsAccepted() throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO();

        when(cookService.updateRecipe(recipeDTO, token)).thenReturn(recipeDTO);

        mockMvc.perform(put("/api/v1/cook/recipe")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipeDTO)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void updateRecipe_InvalidRecipeDTOProvided_returnsBadRequest() throws Exception {
        mockMvc.perform(put("/api/v1/cook/recipe")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().is(611));
    }

    @Test
    public void deleteRecipe_ValidTitleProvided_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/cook/recipe/Test Recipe")
                        .header("Authorization", token))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteRecipe_InvalidTitleProvided_returnsBadRequest() throws Exception {
        mockMvc.perform(delete("/api/v1/cook/recipe/")
                        .header("Authorization", token))
                .andExpect(status().is(611));
    }

    @Test
    public void getRecipeById_ValidIdProvided_returnsAccepted() throws Exception {
        when(cookService.getRecipeById(anyLong(), anyString())).thenReturn(new RecipeDTO());

        mockMvc.perform(get("/api/v1/cook/recipe")
                        .param("id", "1")
                        .header("Authorization", token))
                .andExpect(status().isAccepted());
    }

    @Test
    public void getRecipeById_InvalidIdProvided_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/cook/recipe")
                        .param("id", "invalid")
                        .header("Authorization", token))
                .andExpect(status().is(611));
    }

    @Test
    public void deleteRecipeById_ValidIdProvided_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/v1/cook/recipe")
                        .param("id", "1")
                        .header("Authorization", token))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteRecipeById_InvalidIdProvided_returnsBadRequest() throws Exception {
        mockMvc.perform(delete("/api/v1/cook/recipe")
                        .param("id", "invalid")
                        .header("Authorization", token))
                .andExpect(status().is(611));
    }

    @Test
    public void getRecipesByUser_ValidToken_returnsAccepted() throws Exception {
        when(cookService.getRecipesByUser(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/cook/usr/recipes")
                        .header("Authorization", token))
                .andExpect(status().isAccepted());
    }

    @Test
    public void getUserProfile_ValidUsernameProvided_returnsAccepted() throws Exception {
        UserProfile userProfile = new UserProfile() {
            @Override
            public String getUsername() {
                return "Test";
            }

            @Override
            public String getEmail() {
                return "Test";
            }

            @Override
            public String getFirstName() {
                return "Test";
            }

            @Override
            public String getLastName() {
                return "Test";
            }

            @Override
            public Unit getPowderUnit() {
                return Unit.CUP;
            }

            @Override
            public Unit getLiquidUnit() {
                return Unit.CUP;
            }

            @Override
            public Unit getSolidUnit() {
                return Unit.CUP;
            }

            @Override
            public Unit getOtherUnit() {
                return Unit.CUP;
            }
        };
        when(cookService.getUserProfile(anyString())).thenReturn(userProfile);

        mockMvc.perform(get("/api/v1/cook/usr/profile")
                        .param("username", "testCook")
                        .header("Authorization", token))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getUserProfile_InvalidUsernameProvided_returnsBadRequest() throws Exception {
        when(cookService.getUserProfile(anyString())).thenThrow(new UsernameNotFoundException(""));

        mockMvc.perform(get("/api/v1/cook/usr/profile")
                        .param("username", "invalid")
                        .header("Authorization", token))
                .andExpect(status().is(611));
    }

    @Test
    void saveRecipe_ValidIdProvided_returnsAccepted() throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO();
        when(cookService.saveRecipe(anyLong(), anyString())).thenReturn(recipeDTO);

        mockMvc.perform(post("/api/v1/cook/usr/save")
                        .param("id", "1")
                        .header("Authorization", token))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void saveRecipe_InvalidIdProvided_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/cook/usr/save")
                        .param("id", "invalid")
                        .header("Authorization", token))
                .andExpect(status().is(611));
    }

    @Test
    void getSavedRecipesByUser_ValidToken_returnsAccepted() throws Exception {
        when(cookService.getSavedRecipesByUser(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/cook/usr/SavedRecipes")
                        .header("Authorization", token))
                .andExpect(status().isAccepted());
    }

    @Test
    void getSavedRecipesByUser_InvalidToken_returnsBadRequest() throws Exception {
        when(cookService.getSavedRecipesByUser(anyString())).thenThrow(new IllegalArgumentException());

        mockMvc.perform(get("/api/v1/cook/usr/SavedRecipes")
                        .header("Authorization", "invalid"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void putUserProfile_ValidUserProfileProvided_returnsAccepted() throws Exception {
        CookDTO cookDTO = CookDTO.builder().username("testCook").build();
        when(cookService.updateUserProfile(any(), anyString())).thenReturn(cookDTO);

        mockMvc.perform(put("/api/v1/cook/usr/profile")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cookDTO)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getPublications_whenValidPageAndSizeProvided_returnsAccepted() throws Exception {
        List<PublicationDTO> publicationDTOS = new ArrayList<>();

        when(cookService.getPublicationsByPage(0, 10, token)).thenReturn(publicationDTOS);

        mockMvc.perform(get("/api/v1/cook/publications")
                        .header("Authorization", token)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getPublications_whenInvalidPageAndSizeProvided_returnsBadRequest() throws Exception {
        when(cookService.getPublicationsByPage(-1, -1, token)).thenThrow(new IllegalArgumentException());

        mockMvc.perform(get("/api/v1/cook/publications")
                        .header("Authorization", token)
                        .param("page", "-1")
                        .param("size", "-1"))
                .andExpect(status().is(611));
    }

    @Test
    public void getPublicationByTitle_whenPublicationExists_returnsPublication() throws Exception {
        PublicationDTO publicationDTO = new PublicationDTO();

        when(cookService.getPublicationByTitle("Test Publication", token)).thenReturn(publicationDTO);

        mockMvc.perform(get("/api/v1/cook/publication/Test Publication")
                        .header("Authorization", token))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getPublicationByTitle_whenPublicationDoesNotExist_returnsNotFound() throws Exception {
        when(cookService.getPublicationByTitle("Invalid Publication", token)).thenThrow(new PublicationNotFoundException());

        mockMvc.perform(get("/api/v1/cook/publication/Invalid Publication")
                        .header("Authorization", token))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getPublicationByTitle_whenTitleIsEmpty_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/cook/publication/")
                        .header("Authorization", token))
                .andExpect(status().is(611));
    }

    @Test
    public void createTrick_whenValidInput_returnsTrickDTO() throws Exception {
        TrickDTO trickDTO = new TrickDTO();
        trickDTO.setCookUsername(cook.getUsername());


//        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn(cook.getUsername());
        when(cookRepository.findCookByUsername(trickDTO.getCookUsername())).thenReturn(Optional.of(cook));
        when(cookService.createTrick(any(TrickDTO.class))).thenReturn(new TrickDTO());

        mockMvc.perform(post("/api/v1/cook/trick")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trickDTO)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createTrick_whenInvalidCook_throwsUserNotFoundException() throws Exception {
        TrickDTO trickDTO = new TrickDTO();
        trickDTO.setCookUsername("invalidCook");

        when(cookService.createTrick(any(TrickDTO.class))).thenThrow(new UserNotFoundException());

        mockMvc.perform(post("/api/v1/cook/trick")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trickDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getReactions_whenReactionsExist_returnsReactions() throws Exception {
        List<ReactionDTO> reactionDTOs = new ArrayList<>();

        when(cookService.getReactionsByPublication(anyLong())).thenReturn(reactionDTOs);

        mockMvc.perform(get("/api/v1/cook/reactions/1")
                        .header("Authorization", token))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getReactions_whenNoReactionsExist_returnsEmptyList() throws Exception {
        when(cookService.getReactionsByPublication(anyLong())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/cook/reactions/1")
                        .header("Authorization", token))
                .andExpect(status().isAccepted())
                .andExpect(content().string("[]"));
    }

    @Test
    public void getReactions_whenPublicationDoesNotExist_returnsNotFound() throws Exception {
        when(cookService.getReactionsByPublication(anyLong())).thenThrow(new PublicationNotFoundException());

        mockMvc.perform(get("/api/v1/cook/reactions/1")
                        .header("Authorization", token))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createReaction_whenValidInput_returnsReactionDTO() throws Exception {
        ReactionDTO reactionDTO = new ReactionDTO();
        reactionDTO.setCookUsername(cook.getUsername());
        reactionDTO.setPublicationId(1L);

        when(cookService.createReaction(any(ReactionDTO.class), anyString())).thenReturn(reactionDTO);

        mockMvc.perform(post("/api/v1/cook/react")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reactionDTO)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createReaction_whenInvalidCook_throwsUserNotFoundException() throws Exception {
        ReactionDTO reactionDTO = new ReactionDTO();
        reactionDTO.setCookUsername("invalidCook");

        when(cookService.createReaction(any(ReactionDTO.class), anyString())).thenThrow(new UserNotFoundException());

        mockMvc.perform(post("/api/v1/cook/react")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reactionDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createReaction_whenInvalidPublication_throwsPublicationNotFoundException() throws Exception {
        ReactionDTO reactionDTO = new ReactionDTO();
        reactionDTO.setCookUsername(cook.getUsername());
        reactionDTO.setPublicationId(1L);

        when(cookService.createReaction(any(ReactionDTO.class), anyString())).thenThrow(new PublicationNotFoundException());

        mockMvc.perform(post("/api/v1/cook/react")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reactionDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createReaction_ValidInput_ReturnsAccepted() throws Exception {
        ReactionDTO reactionDTO = new ReactionDTO();
        reactionDTO.setCookUsername("testCook");
        reactionDTO.setPublicationId(1L);

        when(cookService.createReaction(any(ReactionDTO.class), anyString())).thenReturn(reactionDTO);

        mockMvc.perform(post("/api/v1/cook/react")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reactionDTO)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void createReaction_InvalidCook_ReturnsBadRequest() throws Exception {
        ReactionDTO reactionDTO = new ReactionDTO();
        reactionDTO.setCookUsername("invalidCook");

        when(cookService.createReaction(any(ReactionDTO.class), anyString())).thenThrow(new UserNotFoundException());

        mockMvc.perform(post("/api/v1/cook/react")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reactionDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createReaction_InvalidPublication_ReturnsBadRequest() throws Exception {
        ReactionDTO reactionDTO = new ReactionDTO();
        reactionDTO.setCookUsername("testCook");
        reactionDTO.setPublicationId(1L);

        when(cookService.createReaction(any(ReactionDTO.class), anyString())).thenThrow(new PublicationNotFoundException());

        mockMvc.perform(post("/api/v1/cook/react")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(611));
    }

    @Test
    public void getTrickByTitle_ValidTitle_ReturnsTrick() throws Exception {
        String title = "Test Title";

        TrickDTO trickDTO = new TrickDTO();
        trickDTO.setTitle(title);

        when(cookService.getTrickByTitle(token, title)).thenReturn(trickDTO);

        mockMvc.perform(get("/api/v1/cook/trick/{title}", title)
                        .header("Authorization", token))
                .andExpect(status().isAccepted())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(trickDTO)));
    }

    @Test
    public void updateTrick_ValidInput_UpdatesTrick() throws Exception {
        TrickDTO trickDTO = new TrickDTO();
        trickDTO.setId(1L);
        trickDTO.setTitle("Updated Title");

        when(cookService.updateTrick(trickDTO)).thenReturn(trickDTO);

        mockMvc.perform(put("/api/v1/cook/trick")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(trickDTO)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(trickDTO)));
    }

    @Test
    public void deleteTrickById_ValidId_DeletesTrick() throws Exception {
        Long id = 1L;

        when(cookService.deleteTrickById(token, id)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/cook/trick/{id}", id)
                        .header("Authorization", token))
                .andExpect(status().isAccepted())
                .andExpect(content().string("true"));
    }

    @Test
    public void followUser_ValidInput_FollowsUser() throws Exception {
        String username = "testUser";

        FollowerDTO followerDTO = new FollowerDTO();
        followerDTO.setFollowed(CookDTO.builder().username(username).build());

        when(cookService.followCook(username, token)).thenReturn(followerDTO);

        mockMvc.perform(put("/api/v1/cook/usr/follow")
                .header("Authorization", token)
                .param("username", username))
                .andExpect(status().isAccepted())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(followerDTO)));
    }

    @Test
    public void unfollowUser_ValidInput_UnfollowsUser() throws Exception {
        String username = "testUser";

        CookDTO cookDTO = new CookDTO();
        cookDTO.setUsername(username);

        when(cookService.unfollowCook(username, token)).thenReturn(cookDTO);

        mockMvc.perform(delete("/api/v1/cook/usr/unfollow")
                .header("Authorization", token)
                .param("username", username))
                .andExpect(status().isAccepted())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(cookDTO)));
    }

    @Test
    public void getFollowers_ValidUsername_ReturnsFollowers() throws Exception {
        String username = "testUser";

        List<CookDTO> followers = new ArrayList<>();
        CookDTO cookDTO = new CookDTO();
        cookDTO.setUsername(username);
        followers.add(cookDTO);

        when(cookService.getFollowers(username)).thenReturn(followers);

        mockMvc.perform(get("/api/v1/cook/usr/followers")
                        .header("Authorization", token)
                        .param("username", username))
                .andExpect(status().isAccepted())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(followers)));
    }

    @Test
    public void getFollowing_ValidUsername_ReturnsFollowing() throws Exception {
        String username = "testUser";

        List<CookDTO> following = new ArrayList<>();
        CookDTO cookDTO = new CookDTO();
        cookDTO.setUsername(username);
        following.add(cookDTO);

        when(cookService.getFollowing(username)).thenReturn(following);

        mockMvc.perform(get("/api/v1/cook/usr/following")
                        .header("Authorization", token)
                        .param("username", username))
                .andExpect(status().isAccepted())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(following)));
    }
}