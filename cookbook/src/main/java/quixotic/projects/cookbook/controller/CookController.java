package quixotic.projects.cookbook.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Any;
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

//    Reaction
    @GetMapping("/reactions/{id}")
    public ResponseEntity<List<ReactionDTO>> getReactions(@PathVariable("id") Long pubId){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.getReactionsByPublication(pubId));
    }

    @PostMapping("/react")
    public ResponseEntity<ReactionDTO> createReaction(@RequestBody ReactionDTO reactionDTO, @RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.createReaction(reactionDTO, token));
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

    @PutMapping("/usr/follow")
    public ResponseEntity<FollowerDTO> followUser(@PathParam("username") String username, @RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.followCook(username, token));
    }

    @DeleteMapping("/usr/unfollow")
    public ResponseEntity<CookDTO> unfollowUser(@PathParam("username") String username, @RequestHeader("Authorization") String token){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.unfollowCook(username, token));
    }

    @GetMapping("/usr/followers")
    public ResponseEntity<List<CookDTO>> getFollowers(@PathParam("username") String username){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.getFollowers(username));
    }

    @GetMapping("/usr/following")
    public ResponseEntity<List<CookDTO>> getFollowing(@PathParam("username") String username){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.getFollowing(username));
    }
}
