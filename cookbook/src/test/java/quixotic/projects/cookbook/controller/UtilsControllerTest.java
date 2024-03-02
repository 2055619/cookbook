package quixotic.projects.cookbook.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import quixotic.projects.cookbook.model.enums.Unit;
import quixotic.projects.cookbook.service.UtilsService;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UtilsControllerTest {

    @InjectMocks
    private UtilsController utilsController;

    @Mock
    private UtilsService utilsService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(utilsController).build();
    }

    @Test
    public void getValidationPatterns_returnsExpectedResponse() throws Exception {
        Map<String, String> patterns = Map.of("pattern1", "value1");
        when(utilsService.getValidationPatterns()).thenReturn(patterns);

        mockMvc.perform(get("/api/v1/utils/validation-patterns"))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getDifficultyLevels_returnsExpectedResponse() throws Exception {
        List<String> levels = List.of("level1", "level2");
        when(utilsService.getDifficultyLevels()).thenReturn(levels);

        mockMvc.perform(get("/api/v1/utils/difficulty-levels"))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getCategories_returnsExpectedResponse() throws Exception {
        List<String> categories = List.of("category1", "category2");
        when(utilsService.getCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/v1/utils/categories"))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getIngredientStates_returnsExpectedResponse() throws Exception {
        Map<String, List<Unit>> states = Map.of("state1", List.of(Unit.GRAM));
        when(utilsService.getIngredientStates()).thenReturn(states);

        mockMvc.perform(get("/api/v1/utils/ingredient-states"))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getVisibility_returnsExpectedResponse() throws Exception {
        List<String> visibilities = List.of("visibility1", "visibility2");
        when(utilsService.getVisibility()).thenReturn(visibilities);

        mockMvc.perform(get("/api/v1/utils/visibility"))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getPortionSizes_returnsExpectedResponse() throws Exception {
        List<String> portionSizes = List.of("size1", "size2");
        when(utilsService.getPortionSizes()).thenReturn(portionSizes);

        mockMvc.perform(get("/api/v1/utils/portion-sizes"))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getDietTypes_returnsExpectedResponse() throws Exception {
        List<String> dietTypes = List.of("type1", "type2");
        when(utilsService.getDietTypes()).thenReturn(dietTypes);

        mockMvc.perform(get("/api/v1/utils/diet-types"))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}