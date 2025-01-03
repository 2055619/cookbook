package quixotic.projects.cookbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quixotic.projects.cookbook.model.enums.*;
import quixotic.projects.cookbook.validation.ValidationPattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UtilsService {

    public Map<String, String> getValidationPatterns() {
        Map<String, String> patterns = new HashMap<>();
        for (ValidationPattern pattern : ValidationPattern.values()) {
            patterns.put(pattern.name(), pattern.toString());
        }
        return patterns;
    }

    public List<String> getDifficultyLevels() {
        List<String> levels = new ArrayList<>();
        for (DifficultyLevel level : DifficultyLevel.values()) {
            levels.add(level.name());
        }
        return levels;
    }

    public List<String> getCategories() {
        List<String> categories = new ArrayList<>();
        for (RecipeType type : RecipeType.values()) {
            categories.add(type.name());
        }
        return categories;
    }

    public Map<String, List<Unit>> getIngredientStates() {
        Map<String, List<Unit>> states = new HashMap<>();
        for (IngredientState state : IngredientState.values()) {
            states.put(state.name(), state.getUnits());
        }
        return states;
    }

    public List<String> getVisibility() {
        List<String> visibilities = new ArrayList<>();
        for (Visibility visibility : Visibility.values()) {
            visibilities.add(visibility.name());
        }
        return visibilities;
    }

    public List<String> getPortionSizes() {
        List<String> portionSizes = new ArrayList<>();
        for (PortionSize size : PortionSize.values()) {
            portionSizes.add(size.name());
        }
        return portionSizes;
    }

    public List<String> getDietTypes() {
        List<String> dietTypes = new ArrayList<>();
        for (DietType type : DietType.values()) {
            dietTypes.add(type.name());
        }
        return dietTypes;
    }

    public Float convert(Float quantity, Unit from, Unit to) {
        if (quantity == null || quantity <= 0.0f || from == null || to == null) throw new IllegalArgumentException("message.invalid-arguments");
        return switch (to) {
            case TEASPOON -> quantity * from.getTeaspoon();
            case TABLESPOON -> quantity * from.getTablespoon();
            case CUP -> quantity * from.getCup();
            case OUNCE -> quantity * from.getOunce();
            case POUND -> quantity * from.getPound();
            case GRAM -> quantity * from.getGram();
            case KILOGRAM -> quantity * from.getKilogram();
            case MILLILITER -> quantity * from.getMilliliter();
            case LITER -> quantity * from.getLiter();
            case PINCH -> quantity * from.getPinch();
            case DASH -> quantity * from.getDash();
            case NUMBER -> quantity * from.getNumber();
        };
    }

    public List<String> getPublicationType() {
        List<String> pubTypes = new ArrayList<>();
        for (PublicationType publicationType : PublicationType.values()) {
            pubTypes.add(publicationType.name());
        }
        return pubTypes;
    }
}
