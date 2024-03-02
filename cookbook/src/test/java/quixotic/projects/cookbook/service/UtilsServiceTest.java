package quixotic.projects.cookbook.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import quixotic.projects.cookbook.model.enums.*;
import quixotic.projects.cookbook.service.UtilsService;
import quixotic.projects.cookbook.validation.ValidationPattern;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UtilsServiceTest {

    private UtilsService utilsService;

    @BeforeEach
    public void setup() {
        utilsService = new UtilsService();
    }

    @Test
    public void getValidationPatterns_returnsExpectedPatterns() {
        Map<String, String> patterns = utilsService.getValidationPatterns();

        assertNotNull(patterns);
        assertEquals(ValidationPattern.values().length, patterns.size());
    }

    @Test
    public void getDifficultyLevels_returnsExpectedLevels() {
        List<String> levels = utilsService.getDifficultyLevels();

        assertNotNull(levels);
        assertEquals(DifficultyLevel.values().length, levels.size());
    }

    @Test
    public void getCategories_returnsExpectedCategories() {
        List<String> categories = utilsService.getCategories();

        assertNotNull(categories);
        assertEquals(RecipeType.values().length, categories.size());
    }

    @Test
    public void getIngredientStates_returnsExpectedStates() {
        Map<String, List<Unit>> states = utilsService.getIngredientStates();

        assertNotNull(states);
        assertEquals(IngredientState.values().length, states.size());
    }

    @Test
    public void getVisibility_returnsExpectedVisibilities() {
        List<String> visibilities = utilsService.getVisibility();

        assertNotNull(visibilities);
        assertEquals(Visibility.values().length, visibilities.size());
    }

    @Test
    public void getPortionSizes_returnsExpectedSizes() {
        List<String> portionSizes = utilsService.getPortionSizes();

        assertNotNull(portionSizes);
        assertEquals(PortionSize.values().length, portionSizes.size());
    }

    @Test
    public void getDietTypes_returnsExpectedTypes() {
        List<String> dietTypes = utilsService.getDietTypes();

        assertNotNull(dietTypes);
        assertEquals(DietType.values().length, dietTypes.size());
    }
}