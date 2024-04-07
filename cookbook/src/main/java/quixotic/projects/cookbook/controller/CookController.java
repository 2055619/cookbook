package quixotic.projects.cookbook.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quixotic.projects.cookbook.dto.*;
import quixotic.projects.cookbook.model.summary.UserProfile;
import quixotic.projects.cookbook.service.CookService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/cook")
@RequiredArgsConstructor
public class CookController {
    private final CookService cookService;

//    Recipes
    @PostMapping("/recipe")
    public ResponseEntity<RecipeDTO> authenticateCook(@RequestBody RecipeDTO recipeDTO){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.createRecipe(recipeDTO));
    }

    @GetMapping("/recipes")
    public ResponseEntity<List<RecipeDTO>> getRecipes(@PathParam("page") int page, @PathParam("size") int size, @RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.getRecipesByPage(page, size, token));
    }

    @GetMapping("/recipe")
    public ResponseEntity<RecipeDTO> getRecipeById(@PathParam("id") Long id, @RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.getRecipeById(id, token));
    }

    @GetMapping("/recipe/{title}")
    public ResponseEntity<RecipeDTO> getRecipeByTitle(@PathVariable String title, @RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.getRecipeByTitle(title, token));
    }

    @GetMapping("/recipes/title")
    public ResponseEntity<List<RecipeDTO>> getRecipesByTitle(@PathParam("title") String title, @RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.getRecipesSummaryByTitle(title, token));
    }

    @GetMapping("/usr/recipes")
    public ResponseEntity<List<RecipeDTO>> getRecipesByUser(@RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.getRecipesByUser(token));
    }

    @GetMapping("/usr/SavedRecipes")
    public ResponseEntity<List<RecipeDTO>> getSavedRecipesByUser(@RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.getSavedRecipesByUser(token));
    }

    @PostMapping("/usr/save")
    public ResponseEntity<RecipeDTO> saveRecipe(@PathParam("id") Long id, @RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.saveRecipe(id, token));
    }

    @PutMapping("/recipe")
    public ResponseEntity<RecipeDTO> updateRecipe(@RequestBody RecipeDTO recipeDTO, @RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.updateRecipe(recipeDTO, token));
    }

    @DeleteMapping("/recipe")
    public ResponseEntity<Void> deleteRecipeById(@PathParam("id") Long id, @RequestHeader("Authorization") String token){
        cookService.deleteRecipeById(id, token);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/recipe/{title}")
    public ResponseEntity<Void> deleteRecipeByTitle(@PathVariable String title, @RequestHeader("Authorization") String token){
        cookService.deleteRecipeByTitle(title, token);
        return ResponseEntity.noContent().build();
    }

//    Publications
    @GetMapping("/publications")
    public ResponseEntity<List<PublicationDTO>> getPublications(@PathParam("page") int page, @PathParam("size") int size, @RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.getPublicationsByPage(page, size, token));
    }

//    Tricks
    @PostMapping("/trick")
    public ResponseEntity<PublicationDTO> createTrick(@RequestBody TrickDTO trickDTO){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.createTrick(trickDTO));
    }

//    Reaction
    @GetMapping("/reactions/{id}")
    public ResponseEntity<List<ReactionDTO>> getReactions(@PathVariable("id") Long pubId, @RequestHeader("Authorization") String token){
//        @RequestBody PublicationDTO publicationDTO
//        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
//                .body(cookService.getReactionsByPublication(publicationDTO, token));
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.getReactionsByPublication(pubId));
    }

//    @PostMapping("/react")
//    public ResponseEntity<ReactionDTO> createReaction(@RequestBody ReactionDTO reactionDTO, @RequestHeader("Authorization") String token){
//        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
//                .body(cookService.createReaction(reactionDTO, token));
//    }

    @PostMapping("/rate")
    public ResponseEntity<ReactionDTO> ratePublication(@RequestBody ReactionDTO reactionDTO, @RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.ratePublication(reactionDTO, token));
    }

    @PostMapping("/comment")
    public ResponseEntity<ReactionDTO> commentPublication(@RequestBody ReactionDTO reactionDTO, @RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.commentPublication(reactionDTO, token));
    }

//    User
    @GetMapping("/usr/profile")
    public ResponseEntity<UserProfile> getUserProfile(@PathParam("username") String username){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.getUserProfile(username));
    }

    @PutMapping("/usr/profile")
    public ResponseEntity<CookDTO> updateUserProfile(@RequestBody CookDTO cookDTO, @RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.updateUserProfile(cookDTO, token));
    }
}
