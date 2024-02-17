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

    public Map<String,String> getValidationPatterns(){
        Map<String,String> patterns = new HashMap<>();
        for (ValidationPattern pattern: ValidationPattern.values()) {
            patterns.put(pattern.name(),pattern.toString());
        }
        return patterns;
    }

    public List<String> getDifficultyLevels(){
        List<String> levels = new ArrayList<>();
        for (DifficultyLevel level: DifficultyLevel.values()) {
            levels.add(level.name());
        }
        return levels;
    }

    public List<String> getCategories(){
        List<String> categories = new ArrayList<>();
        for (RecipeType type: RecipeType.values()) {
            categories.add(type.name());
        }
        return categories;
    }

    public Map<String, List<Unit>> getIngredientStates(){
        Map<String, List<Unit>> states = new HashMap<>();
        for (IngredientState state: IngredientState.values()) {
            states.put(state.name(),state.getUnits());
        }
        return states;
    }

    public List<String> getVisibility(){
        List<String> visibilities = new ArrayList<>();
        for (Visibility visibility: Visibility.values()) {
            visibilities.add(visibility.name());
        }
        return visibilities;
    }
}
