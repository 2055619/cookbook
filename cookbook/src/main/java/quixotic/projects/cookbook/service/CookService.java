package quixotic.projects.cookbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import quixotic.projects.cookbook.dto.LoginDTO;
import quixotic.projects.cookbook.dto.SignInDTO;
import quixotic.projects.cookbook.repository.CookRepository;
import quixotic.projects.cookbook.security.JwtTokenProvider;

@RequiredArgsConstructor
@Service
public class CookService {
    private final CookRepository cookRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    public String authenticateCook(LoginDTO loginDto){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        return jwtTokenProvider.generateToken(authentication);
    }

    public void createCook(SignInDTO signInDTO){
        cookRepository.save(signInDTO.toCook());
    }

}
