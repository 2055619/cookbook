package quixotic.projects.cookbook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import quixotic.projects.cookbook.model.enums.Unit;
import quixotic.projects.cookbook.repository.CookRepository;
import quixotic.projects.cookbook.security.JwtAuthenticationEntryPoint;
import quixotic.projects.cookbook.security.JwtTokenProvider;
import quixotic.projects.cookbook.security.SecurityConfiguration;
import quixotic.projects.cookbook.service.UtilsService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig(classes = {UtilsController.class, CookbookExceptionHandler.class, SecurityConfiguration.class})
@WebMvcTest(UtilsController.class)
public class UtilsControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UtilsService utilsService;
    @MockBean
    private JwtTokenProvider jwtTokenProvider;
    @MockBean
    private CookRepository cookRepository;
    @MockBean
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    private ObjectMapper objectMapper;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void shouldReturnValidationPatterns() throws Exception {
        Map<String, String> mockPatterns = new HashMap<>();
        mockPatterns.put("pattern1", "value1");
        mockPatterns.put("pattern2", "value2");

        when(utilsService.getValidationPatterns()).thenReturn(mockPatterns);

        mockMvc.perform(get("/api/v1/utils/validation-patterns"))
                .andExpect(status().isAccepted())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(mockPatterns)));
    }

    @Test
    public void shouldReturnDifficultyLevels() throws Exception {
        List<String> mockLevels = Arrays.asList("Easy", "Medium", "Hard");

        when(utilsService.getDifficultyLevels()).thenReturn(mockLevels);

        mockMvc.perform(get("/api/v1/utils/difficulty-levels"))
                .andExpect(status().isAccepted())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(mockLevels)));
    }

    @Test
    public void shouldReturnCategories() throws Exception {
        List<String> mockCategories = Arrays.asList("Category1", "Category2", "Category3");

        when(utilsService.getCategories()).thenReturn(mockCategories);

        mockMvc.perform(get("/api/v1/utils/categories"))
                .andExpect(status().isAccepted())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(mockCategories)));
    }

    @Test
    public void shouldReturnIngredientStates() throws Exception {
        Map<String, List<Unit>> mockStates = new HashMap<>();
        mockStates.put("state1", Arrays.asList(Unit.GRAM, Unit.KILOGRAM));
        mockStates.put("state2", Arrays.asList(Unit.LITER, Unit.MILLILITER));

        when(utilsService.getIngredientStates()).thenReturn(mockStates);

        mockMvc.perform(get("/api/v1/utils/ingredient-states"))
                .andExpect(status().isAccepted())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(mockStates)));
    }

    @Test
    public void shouldReturnVisibility() throws Exception {
        List<String> mockVisibility = Arrays.asList("Public", "Private");

        when(utilsService.getVisibility()).thenReturn(mockVisibility);

        mockMvc.perform(get("/api/v1/utils/visibility"))
                .andExpect(status().isAccepted())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(mockVisibility)));
    }

    @Test
    public void shouldReturnPortionSizes() throws Exception {
        List<String> mockSizes = Arrays.asList("Small", "Medium", "Large");

        when(utilsService.getPortionSizes()).thenReturn(mockSizes);

        mockMvc.perform(get("/api/v1/utils/portion-sizes"))
                .andExpect(status().isAccepted())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(mockSizes)));
    }

    @Test
    public void shouldReturnDietTypes() throws Exception {
        List<String> mockTypes = Arrays.asList("Vegan", "Vegetarian", "Pescatarian");

        when(utilsService.getDietTypes()).thenReturn(mockTypes);

        mockMvc.perform(get("/api/v1/utils/diet-types"))
                .andExpect(status().isAccepted())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(mockTypes)));
    }

    @Test
    public void getConversion_ValidQuantityAndUnitsProvided_returnsAccepted() throws Exception {
        when(utilsService.convert(anyFloat(), any(Unit.class), any(Unit.class))).thenReturn(1.0f);

        mockMvc.perform(get("/api/v1/utils/conversion")
                        .param("quantity", "1")
                        .param("from", "GRAM")
                        .param("to", "KILOGRAM"))
                .andExpect(status().isAccepted());
    }

    @Test
    public void getConversion_InvalidQuantityProvided_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/utils/conversion")
                        .param("quantity", "invalid")
                        .param("from", "GRAM")
                        .param("to", "KILOGRAM"))
                .andExpect(status().is(611));
    }

    @Test
    public void getConversion_InvalidFromUnitProvided_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/utils/conversion")
                        .param("quantity", "1")
                        .param("from", "INVALID")
                        .param("to", "KILOGRAM"))
                .andExpect(status().is(611));
    }

    @Test
    public void getConversion_InvalidToUnitProvided_returnsBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/utils/conversion")
                        .param("quantity", "1")
                        .param("from", "GRAM")
                        .param("to", "INVALID"))
                .andExpect(status().is(611));
    }


}