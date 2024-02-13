package quixotic.projects.cookbook.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import quixotic.projects.cookbook.dto.CookDTO;
import quixotic.projects.cookbook.dto.LoginDTO;
import quixotic.projects.cookbook.dto.SignInDTO;
import quixotic.projects.cookbook.service.CookService;

@Controller
@RequestMapping("/api/v1/cook")
@RequiredArgsConstructor
public class CookController {
    private final CookService cookService;

    @PostMapping("/login")
    public ResponseEntity<CookDTO> authenticateCook(@RequestBody LoginDTO loginDTO){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.authenticateCook(loginDTO));
    }

    @PostMapping("/signin")
    public ResponseEntity<CookDTO> signinCook(@RequestBody SignInDTO signInDTO){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(cookService.createCook(signInDTO));
    }

}
