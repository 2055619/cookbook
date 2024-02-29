package quixotic.projects.cookbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quixotic.projects.cookbook.dto.CookDTO;
import quixotic.projects.cookbook.dto.RecipeDTO;
import quixotic.projects.cookbook.dto.SignInDTO;
import quixotic.projects.cookbook.dto.SignUpDTO;
import quixotic.projects.cookbook.service.CookService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/cook")
@RequiredArgsConstructor
public class CookController {
    private final CookService cookService;

    @PostMapping("/recipe")
    public ResponseEntity<RecipeDTO> authenticateCook(@RequestBody RecipeDTO recipeDTO){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.createRecipe(recipeDTO));
    }

    @GetMapping("/recipes")
    public ResponseEntity<List<RecipeDTO>> getRecipes(){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.getRecipes());
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<RecipeDTO> getRecipes(@PathVariable Long id){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.getRecipe(id));
    }

    @PutMapping("/recipe")
    public ResponseEntity<RecipeDTO> updateRecipe(@RequestBody RecipeDTO recipeDTO){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.updateRecipe(recipeDTO));
    }

    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id){
        cookService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
