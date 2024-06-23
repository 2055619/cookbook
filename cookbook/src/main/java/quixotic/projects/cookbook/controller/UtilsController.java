package quixotic.projects.cookbook.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import quixotic.projects.cookbook.dto.ContactDTO;
import quixotic.projects.cookbook.model.enums.Unit;
import quixotic.projects.cookbook.service.EmailService;
import quixotic.projects.cookbook.service.UtilsService;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/utils")
public class UtilsController {

    private final UtilsService utilsService;
    private final EmailService emailService;

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
    public ResponseEntity<Map<String, List<Unit>>> getIngrediantStates() {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(utilsService.getIngredientStates());
    }

    @GetMapping("/visibility")
    public ResponseEntity<List<String>> getVisibility() {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(utilsService.getVisibility());
    }

    @GetMapping("portion-sizes")
    public ResponseEntity<List<String>> getPortionSizes() {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(utilsService.getPortionSizes());
    }

    @GetMapping("diet-types")
    public ResponseEntity<List<String>> getDietTypes() {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(utilsService.getDietTypes());
    }

    @GetMapping("publication-types")
    public ResponseEntity<List<String>> getPublicationTypes() {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(utilsService.getPublicationType());
    }

    @GetMapping("conversion")
    public ResponseEntity<Float> getConversion(@PathParam("quantity") Float quantity, @PathParam("from") Unit from, @PathParam("to") Unit to) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(utilsService.convert(quantity, from, to));
    }

    @PostMapping("contact")
    public ResponseEntity<String> contactUs(@RequestBody ContactDTO contactDTO) {
        emailService.sendEmail(contactDTO);
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON).body("Email sent");
    }

}
