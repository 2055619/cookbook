package quixotic.projects.cookbook.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quixotic.projects.cookbook.dto.CookDTO;
import quixotic.projects.cookbook.dto.RecipeDTO;
import quixotic.projects.cookbook.dto.SignInDTO;
import quixotic.projects.cookbook.dto.SignUpDTO;
import quixotic.projects.cookbook.model.summary.RecipeSummary;
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
    public ResponseEntity<List<RecipeDTO>> getRecipes(@PathParam("page") int page, @PathParam("size") int size){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.getRecipes(page, size));
    }

    @GetMapping("/recipe/{title}")
    public ResponseEntity<RecipeDTO> getRecipes(@PathVariable String title){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.getRecipe(title));
    }

    @GetMapping("/recipes/title")
    public ResponseEntity<List<RecipeSummary>> getRecipesByTitle(@PathParam("title") String title){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.getRecipesByTitle(title));
    }

    @PutMapping("/recipe")
    public ResponseEntity<RecipeDTO> updateRecipe(@RequestBody RecipeDTO recipeDTO){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.updateRecipe(recipeDTO));
    }

    @DeleteMapping("/recipe/{title}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable String title){
        cookService.deleteRecipe(title);
        return ResponseEntity.noContent().build();
    }
}
