package quixotic.projects.cookbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import quixotic.projects.cookbook.model.enums.IngredientState;
import quixotic.projects.cookbook.model.enums.Unit;
import quixotic.projects.cookbook.service.UtilsService;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/utils")
public class UtilsController {

    private final UtilsService utilsService;

    @GetMapping("/validation-patterns")
    public ResponseEntity<Map<String, String>> getValidationPatterns() {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(utilsService.getValidationPatterns());
    }

    @GetMapping("/difficulty-levels")
    public ResponseEntity<List<String>> getDifficultyLevels() {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(utilsService.getDifficultyLevels());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(utilsService.getCategories());
    }

    @GetMapping("/ingredient-states")
    public ResponseEntity<Map<String, List<Unit>>> getUnits() {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(utilsService.getIngredientStates());
    }

    @GetMapping("/visibility")
    public ResponseEntity<List<String>> getVisibility() {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(utilsService.getVisibility());
    }

}
