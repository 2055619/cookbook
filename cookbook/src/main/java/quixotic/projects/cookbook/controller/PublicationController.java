package quixotic.projects.cookbook.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Any;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quixotic.projects.cookbook.dto.PublicationDTO;
import quixotic.projects.cookbook.dto.RecipeDTO;
import quixotic.projects.cookbook.dto.TrickDTO;
import quixotic.projects.cookbook.service.PublicationService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/pubs")
@RequiredArgsConstructor
public class PublicationController {
    private final PublicationService publicationService;

    //    Publications
    @GetMapping("/publications")
    public ResponseEntity<List<PublicationDTO>> getPublications(@PathParam("page") int page, @PathParam("size") int size, @RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(publicationService.getPublicationsByPage(page, size, token));
    }

    @GetMapping("/publication/{title}")
    public ResponseEntity<PublicationDTO> getPublicationByTitle(@PathVariable String title, @RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(publicationService.getPublicationByTitle(title, token));
    }

    @DeleteMapping("/publication")
    public ResponseEntity<Any> deletePublicationById(@PathParam("id") Long id, @RequestHeader("Authorization") String token){
        publicationService.deletePublicationById(token, id);
        return ResponseEntity.noContent().build();
    }

    //    Recipes
    @PostMapping("/recipe")
    public ResponseEntity<RecipeDTO> authenticateCook(@RequestBody RecipeDTO recipeDTO){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(publicationService.createRecipe(recipeDTO));
    }

    @GetMapping("/recipes")
    public ResponseEntity<List<RecipeDTO>> getRecipes(@PathParam("page") int page, @PathParam("size") int size, @RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(publicationService.getRecipesByPage(page, size, token));
    }

    @GetMapping("/recipe")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathParam("id") Long id, @RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(publicationService.getRecipeById(id, token));
    }

    @GetMapping("/recipe/{title}")
    public ResponseEntity<RecipeDTO> getRecipeByTitle(@PathVariable String title, @RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(publicationService.getRecipeByTitle(title, token));
    }

    @GetMapping("/recipes/title")
    public ResponseEntity<List<RecipeDTO>> getRecipesByTitle(@PathParam("title") String title, @RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(publicationService.getRecipesSummaryByTitle(title, token));
    }

    @GetMapping("/usr/recipes")
    public ResponseEntity<List<RecipeDTO>> getRecipesByUser(@RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(publicationService.getRecipesByUser(token));
    }

    @GetMapping("/usr/SavedRecipes")
    public ResponseEntity<List<RecipeDTO>> getSavedRecipesByUser(@RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(publicationService.getSavedRecipesByUser(token));
    }

    @PostMapping("/usr/save")
    public ResponseEntity<RecipeDTO> saveRecipe(@PathParam("id") Long id, @RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(publicationService.saveRecipe(id, token));
    }

    @PutMapping("/recipe")
    public ResponseEntity<RecipeDTO> updateRecipe(@RequestBody RecipeDTO recipeDTO, @RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(publicationService.updateRecipe(recipeDTO, token));
    }

    @DeleteMapping("/recipe")
    public ResponseEntity<Void> deleteRecipeById(@PathParam("id") Long id, @RequestHeader("Authorization") String token){
        publicationService.deleteRecipeById(id, token);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/recipe/{title}")
    public ResponseEntity<Void> deleteRecipeByTitle(@PathVariable String title, @RequestHeader("Authorization") String token){
        publicationService.deleteRecipeByTitle(title, token);
        return ResponseEntity.noContent().build();
    }

    //    Tricks
    @PostMapping("/trick")
    public ResponseEntity<PublicationDTO> createTrick(@RequestBody TrickDTO trickDTO){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(publicationService.createTrick(trickDTO));
    }

    @GetMapping("/trick/{title}")
    public ResponseEntity<PublicationDTO> getTrickByTitle(@RequestHeader("Authorization") String token, @PathVariable String title){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(publicationService.getTrickByTitle(token, title));
    }

    @PutMapping("/trick")
    public ResponseEntity<PublicationDTO> updateTrick(@RequestBody TrickDTO trickDTO){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(publicationService.updateTrick(trickDTO));
    }

    @DeleteMapping("/trick")
    public ResponseEntity<Boolean> getTricks(@RequestHeader("Authorization") String token, @PathParam("id") Long id){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(publicationService.deleteTrickById(token, id));
    }


}
