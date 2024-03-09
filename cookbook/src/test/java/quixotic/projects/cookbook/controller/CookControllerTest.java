package quixotic.projects.cookbook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import quixotic.projects.cookbook.dto.RecipeDTO;
import quixotic.projects.cookbook.exception.badRequestException.RecipeNotFoundException;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.enums.Unit;
import quixotic.projects.cookbook.model.summary.RecipeSummary;
import quixotic.projects.cookbook.repository.CookRepository;
import quixotic.projects.cookbook.security.JwtAuthenticationEntryPoint;
import quixotic.projects.cookbook.security.JwtTokenProvider;
import quixotic.projects.cookbook.security.Role;
import quixotic.projects.cookbook.security.SecurityConfiguration;
import quixotic.projects.cookbook.service.CookService;
import quixotic.projects.cookbook.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
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
        List<RecipeSummary> recipeSummaries = new ArrayList<>();

        when(cookService.getRecipesSummaryByTitle(anyString())).thenReturn(recipeSummaries);

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
        doThrow(new IllegalArgumentException()).when(cookService).getRecipesByPage(-1, -1, "testCook");
        mockMvc.perform(get("/api/v1/cook/recipes")
                        .header("Authorization", token)
                        .param("page", "-1")
                        .param("size", "-1"))
                .andExpect(status().is(611));
    }

    @Test
    public void getRecipe_ValidTitleProvided_returnsAccepted() throws Exception {
        RecipeDTO recipeDTO = new RecipeDTO();

        when(cookService.getRecipeByTitle("Test Recipe")).thenReturn(recipeDTO);

        mockMvc.perform(get("/api/v1/cook/recipe/Test Recipe")
                        .header("Authorization", token))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getRecipeByTitle_InvalidTitleProvided_returnsBadRequest() throws Exception {
        doThrow(new RecipeNotFoundException()).when(cookService).getRecipeByTitle(anyString());

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

        when(cookService.updateRecipe(recipeDTO)).thenReturn(recipeDTO);

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

}