package quixotic.projects.cookbook.service;

import org.junit.jupiter.api.Test;
import quixotic.projects.cookbook.model.enums.*;
import quixotic.projects.cookbook.validation.ValidationPattern;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsServiceTest {

    private final UtilsService utilsService = new UtilsService();

//    @BeforeEach
//    public void setup() {
//        utilsService = new UtilsService();
//    }

    @Test
    public void getValid_ationPatterns_returnsExpectedPatterns() {
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

//    Conversion tests
    @Test
    public void convertValid_TeaspoonToAllUnits() {
        assertEquals(1f, utilsService.convert(1f, Unit.TEASPOON, Unit.TEASPOON));
        assertEquals(0.33333334f, utilsService.convert(1f, Unit.TEASPOON, Unit.TABLESPOON));
        assertEquals(0.0208333f, utilsService.convert(1f, Unit.TEASPOON, Unit.CUP));
        assertEquals(0.166667f, utilsService.convert(1f, Unit.TEASPOON, Unit.OUNCE));
        assertEquals(0.0105f, utilsService.convert(1f, Unit.TEASPOON, Unit.POUND));
        assertEquals(5.69f, utilsService.convert(1f, Unit.TEASPOON, Unit.GRAM));
        assertEquals(0.004928921594f, utilsService.convert(1f, Unit.TEASPOON, Unit.KILOGRAM));
        assertEquals(4.92892f, utilsService.convert(1f, Unit.TEASPOON, Unit.MILLILITER));
        assertEquals(0.004929f, utilsService.convert(1f, Unit.TEASPOON, Unit.LITER));
        assertEquals(16.0f, utilsService.convert(1f, Unit.TEASPOON, Unit.PINCH));
        assertEquals(8.0f, utilsService.convert(1f, Unit.TEASPOON, Unit.DASH));
        assertEquals(1f, utilsService.convert(1f, Unit.TEASPOON, Unit.NUMBER));
    }

    @Test
    public void convertValid_TablespoonToAllUnits() {
        assertEquals(3f, utilsService.convert(1f, Unit.TABLESPOON, Unit.TEASPOON));
        assertEquals(1f, utilsService.convert(1f, Unit.TABLESPOON, Unit.TABLESPOON));
        assertEquals(0.0625f, utilsService.convert(1f, Unit.TABLESPOON, Unit.CUP));
        assertEquals(0.5f, utilsService.convert(1f, Unit.TABLESPOON, Unit.OUNCE));
        assertEquals(0.03125f, utilsService.convert(1f, Unit.TABLESPOON, Unit.POUND));
        assertEquals(14.7868f, utilsService.convert(1f, Unit.TABLESPOON, Unit.GRAM));
        assertEquals(0.125f, utilsService.convert(1f, Unit.TABLESPOON, Unit.KILOGRAM));
        assertEquals(14.7868f, utilsService.convert(1f, Unit.TABLESPOON, Unit.MILLILITER));
        assertEquals(0.0147868f, utilsService.convert(1f, Unit.TABLESPOON, Unit.LITER));
        assertEquals(48.0f, utilsService.convert(1f, Unit.TABLESPOON, Unit.PINCH));
        assertEquals(24.0f, utilsService.convert(1f, Unit.TABLESPOON, Unit.DASH));
        assertEquals(3.0f, utilsService.convert(1f, Unit.TABLESPOON, Unit.NUMBER));
    }
    @Test
    public void convertValid_CupToAllUnits() {
        assertEquals(48f, utilsService.convert(1f, Unit.CUP, Unit.TEASPOON));
        assertEquals(16f, utilsService.convert(1f, Unit.CUP, Unit.TABLESPOON));
        assertEquals(1f, utilsService.convert(1f, Unit.CUP, Unit.CUP));
        assertEquals(8.11537f, utilsService.convert(1f, Unit.CUP, Unit.OUNCE));
        assertEquals(0.0625f, utilsService.convert(1f, Unit.CUP, Unit.POUND));
        assertEquals(236.588f, utilsService.convert(1f, Unit.CUP, Unit.GRAM));
        assertEquals(0.236588f, utilsService.convert(1f, Unit.CUP, Unit.KILOGRAM));
        assertEquals(236.588f, utilsService.convert(1f, Unit.CUP, Unit.MILLILITER));
        assertEquals(0.24f, utilsService.convert(1f, Unit.CUP, Unit.LITER));
        assertEquals(192f, utilsService.convert(1f, Unit.CUP, Unit.PINCH));
        assertEquals(384f, utilsService.convert(1f, Unit.CUP, Unit.DASH));
        assertEquals(48f, utilsService.convert(1f, Unit.CUP, Unit.NUMBER));
    }

    @Test
    public void convertValid_OunceToAllUnits() {
        assertEquals(6f, utilsService.convert(1f, Unit.OUNCE, Unit.TEASPOON));
        assertEquals(2f, utilsService.convert(1f, Unit.OUNCE, Unit.TABLESPOON));
        assertEquals(0.123224f, utilsService.convert(1f, Unit.OUNCE, Unit.CUP));
        assertEquals(1f, utilsService.convert(1f, Unit.OUNCE, Unit.OUNCE));
        assertEquals(0.0625f, utilsService.convert(1f, Unit.OUNCE, Unit.POUND));
        assertEquals(29.5735f, utilsService.convert(1f, Unit.OUNCE, Unit.GRAM));
        assertEquals(0.0295735f, utilsService.convert(1f, Unit.OUNCE, Unit.KILOGRAM));
        assertEquals(29.5735f, utilsService.convert(1f, Unit.OUNCE, Unit.MILLILITER));
        assertEquals(0.0295735f, utilsService.convert(1f, Unit.OUNCE, Unit.LITER));
        assertEquals(96f, utilsService.convert(1f, Unit.OUNCE, Unit.PINCH));
        assertEquals(48f, utilsService.convert(1f, Unit.OUNCE, Unit.DASH));
        assertEquals(6f, utilsService.convert(1f, Unit.OUNCE, Unit.NUMBER));
    }

    @Test
    public void convertValid_PoundToAllUnits() {
        assertEquals(95.2f, utilsService.convert(1f, Unit.POUND, Unit.TEASPOON));
        assertEquals(31.733334f, utilsService.convert(1f, Unit.POUND, Unit.TABLESPOON));
        assertEquals(1.9835f, utilsService.convert(1f, Unit.POUND, Unit.CUP));
        assertEquals(16f, utilsService.convert(1f, Unit.POUND, Unit.OUNCE));
        assertEquals(1f, utilsService.convert(1f, Unit.POUND, Unit.POUND));
        assertEquals(453.592f, utilsService.convert(1f, Unit.POUND, Unit.GRAM));
        assertEquals(0.453592f, utilsService.convert(1f, Unit.POUND, Unit.KILOGRAM));
        assertEquals(453.592f, utilsService.convert(1f, Unit.POUND, Unit.MILLILITER));
        assertEquals(0.453592f, utilsService.convert(1f, Unit.POUND, Unit.LITER));
        assertEquals(1824f, utilsService.convert(1f, Unit.POUND, Unit.PINCH));
        assertEquals(912f, utilsService.convert(1f, Unit.POUND, Unit.DASH));
        assertEquals(95.2f, utilsService.convert(1f, Unit.POUND, Unit.NUMBER));
    }

    @Test
    public void convertValid_GramToAllUnits() {
        assertEquals(0.1756515f, utilsService.convert(1f, Unit.GRAM, Unit.TEASPOON));
        assertEquals(0.0585505f, utilsService.convert(1f, Unit.GRAM, Unit.TABLESPOON));
        assertEquals(0.00366f, utilsService.convert(1f, Unit.GRAM, Unit.CUP));
        assertEquals(0.02835f, utilsService.convert(1f, Unit.GRAM, Unit.OUNCE));
        assertEquals(0.0022f, utilsService.convert(1f, Unit.GRAM, Unit.POUND));
        assertEquals(1f, utilsService.convert(1f, Unit.GRAM, Unit.GRAM));
        assertEquals(0.001f, utilsService.convert(1f, Unit.GRAM, Unit.KILOGRAM));
        assertEquals(1.0416667f, utilsService.convert(1f, Unit.GRAM, Unit.MILLILITER));
        assertEquals(0.001042f, utilsService.convert(1f, Unit.GRAM, Unit.LITER));
        assertEquals(3.333f, utilsService.convert(1f, Unit.GRAM, Unit.PINCH));
        assertEquals(1.666f, utilsService.convert(1f, Unit.GRAM, Unit.DASH));
        assertEquals(0.208333333f, utilsService.convert(1f, Unit.GRAM, Unit.NUMBER));
    }

    @Test
    public void convertValid_KilogramToAllUnits() {
        assertEquals(384f, utilsService.convert(1f, Unit.KILOGRAM, Unit.TEASPOON));
        assertEquals(128f, utilsService.convert(1f, Unit.KILOGRAM, Unit.TABLESPOON));
        assertEquals(8f, utilsService.convert(1f, Unit.KILOGRAM, Unit.CUP));
        assertEquals(64f, utilsService.convert(1f, Unit.KILOGRAM, Unit.OUNCE));
        assertEquals(4f, utilsService.convert(1f, Unit.KILOGRAM, Unit.POUND));
        assertEquals(1000f, utilsService.convert(1f, Unit.KILOGRAM, Unit.GRAM));
        assertEquals(1f, utilsService.convert(1f, Unit.KILOGRAM, Unit.KILOGRAM));
        assertEquals(1000f, utilsService.convert(1f, Unit.KILOGRAM, Unit.MILLILITER));
        assertEquals(1f, utilsService.convert(1f, Unit.KILOGRAM, Unit.LITER));
        assertEquals(3200f, utilsService.convert(1f, Unit.KILOGRAM, Unit.PINCH));
        assertEquals(1600f, utilsService.convert(1f, Unit.KILOGRAM, Unit.DASH));
        assertEquals(200f, utilsService.convert(1f, Unit.KILOGRAM, Unit.NUMBER));
    }

    @Test
    public void convertValid_MilliliterToAllUnits() {
        assertEquals(0.202884f, utilsService.convert(1f, Unit.MILLILITER, Unit.TEASPOON));
        assertEquals(0.067628f, utilsService.convert(1f, Unit.MILLILITER, Unit.TABLESPOON));
        assertEquals(0.00422f, utilsService.convert(1f, Unit.MILLILITER, Unit.CUP));
        assertEquals(0.033814f, utilsService.convert(1f, Unit.MILLILITER, Unit.OUNCE));
        assertEquals(0.0021f, utilsService.convert(1f, Unit.MILLILITER, Unit.POUND));
        assertEquals(0.03527f, utilsService.convert(1f, Unit.MILLILITER, Unit.GRAM));
        assertEquals(0.001f, utilsService.convert(1f, Unit.MILLILITER, Unit.KILOGRAM));
        assertEquals(1f, utilsService.convert(1f, Unit.MILLILITER, Unit.MILLILITER));
        assertEquals(0.001f, utilsService.convert(1f, Unit.MILLILITER, Unit.LITER));
        assertEquals(3.218f, utilsService.convert(1f, Unit.MILLILITER, Unit.PINCH));
        assertEquals(1.609f, utilsService.convert(1f, Unit.MILLILITER, Unit.DASH));
        assertEquals(0.202884f, utilsService.convert(1f, Unit.MILLILITER, Unit.NUMBER));
    }

    @Test
    public void convertValid_LiterToAllUnits() {
        assertEquals(202.884f, utilsService.convert(1f, Unit.LITER, Unit.TEASPOON));
        assertEquals(67.628f, utilsService.convert(1f, Unit.LITER, Unit.TABLESPOON));
        assertEquals(4.167f, utilsService.convert(1f, Unit.LITER, Unit.CUP));
        assertEquals(33.814f, utilsService.convert(1f, Unit.LITER, Unit.OUNCE));
        assertEquals(1.05669f, utilsService.convert(1f, Unit.LITER, Unit.POUND));
        assertEquals(1000f, utilsService.convert(1f, Unit.LITER, Unit.GRAM));
        assertEquals(1f, utilsService.convert(1f, Unit.LITER, Unit.KILOGRAM));
        assertEquals(1000f, utilsService.convert(1f, Unit.LITER, Unit.MILLILITER));
        assertEquals(1f, utilsService.convert(1f, Unit.LITER, Unit.LITER));
        assertEquals(3200f, utilsService.convert(1f, Unit.LITER, Unit.PINCH));
        assertEquals(1600f, utilsService.convert(1f, Unit.LITER, Unit.DASH));
        assertEquals(200f, utilsService.convert(1f, Unit.LITER, Unit.NUMBER));
    }

    @Test
    public void convertValid_PinchToAllUnits() {
        assertEquals(0.203f, utilsService.convert(1f, Unit.PINCH, Unit.TEASPOON));
        assertEquals(0.0676667f, utilsService.convert(1f, Unit.PINCH, Unit.TABLESPOON));
        assertEquals(0.00421946f, utilsService.convert(1f, Unit.PINCH, Unit.CUP));
        assertEquals(0.033814f, utilsService.convert(1f, Unit.PINCH, Unit.OUNCE));
        assertEquals(0.0021f, utilsService.convert(1f, Unit.PINCH, Unit.POUND));
        assertEquals(0.035274f, utilsService.convert(1f, Unit.PINCH, Unit.GRAM));
        assertEquals(0.0000439142f, utilsService.convert(1f, Unit.PINCH, Unit.KILOGRAM));
        assertEquals(0.0520417f, utilsService.convert(1f, Unit.PINCH, Unit.MILLILITER));
        assertEquals(0.000052f, utilsService.convert(1f, Unit.PINCH, Unit.LITER));
        assertEquals(1f, utilsService.convert(1f, Unit.PINCH, Unit.PINCH));
        assertEquals(0.5f, utilsService.convert(1f, Unit.PINCH, Unit.DASH));
        assertEquals(0.0625f, utilsService.convert(1f, Unit.PINCH, Unit.NUMBER));
    }

    @Test
    public void convertValid_DashToAllUnits() {
        assertEquals(0.1015f, utilsService.convert(1f, Unit.DASH, Unit.TEASPOON));
        assertEquals(0.0416666666666667f, utilsService.convert(1f, Unit.DASH, Unit.TABLESPOON));
        assertEquals(0.0026041666666667f, utilsService.convert(1f, Unit.DASH, Unit.CUP));
        assertEquals(0.0208333333333333f, utilsService.convert(1f, Unit.DASH, Unit.OUNCE));
        assertEquals(0.00105f, utilsService.convert(1f, Unit.DASH, Unit.POUND));
        assertEquals(0.017637f, utilsService.convert(1f, Unit.DASH, Unit.GRAM));
        assertEquals(0.0000219571f, utilsService.convert(1f, Unit.DASH, Unit.KILOGRAM));
        assertEquals(0.0260209f, utilsService.convert(1f, Unit.DASH, Unit.MILLILITER));
        assertEquals(0.000625f, utilsService.convert(1f, Unit.DASH, Unit.LITER));
        assertEquals(8f, utilsService.convert(1f, Unit.DASH, Unit.PINCH));
        assertEquals(1f, utilsService.convert(1f, Unit.DASH, Unit.DASH));
        assertEquals(0.125f, utilsService.convert(1f, Unit.DASH, Unit.NUMBER));
    }

    @Test
    public void convertValid_NumberToAllUnits() {
        assertEquals(0.0625f, utilsService.convert(1f, Unit.NUMBER, Unit.TEASPOON));
        assertEquals(0.0208333f, utilsService.convert(1f, Unit.NUMBER, Unit.TABLESPOON));
        assertEquals(0.333333f, utilsService.convert(1f, Unit.NUMBER, Unit.CUP));
        assertEquals(0.0105042016806723f, utilsService.convert(1f, Unit.NUMBER, Unit.OUNCE));
        assertEquals(0.0105f, utilsService.convert(1f, Unit.NUMBER, Unit.POUND));
        assertEquals(0.005f, utilsService.convert(1f, Unit.NUMBER, Unit.GRAM));
        assertEquals(0.0005f, utilsService.convert(1f, Unit.NUMBER, Unit.KILOGRAM));
        assertEquals(4.92610837438423f, utilsService.convert(1f, Unit.NUMBER, Unit.MILLILITER));
        assertEquals(1000f, utilsService.convert(1f, Unit.NUMBER, Unit.LITER));
        assertEquals(16f, utilsService.convert(1f, Unit.NUMBER, Unit.PINCH));
        assertEquals(8f, utilsService.convert(1f, Unit.NUMBER, Unit.DASH));
        assertEquals(1f, utilsService.convert(1f, Unit.NUMBER, Unit.NUMBER));
    }

    @Test
    public void convert_throwsExceptionWhenConvertingNullQuantity() {
        assertThrows(IllegalArgumentException.class, () -> utilsService.convert(null, Unit.CUP, Unit.OUNCE));
    }

}