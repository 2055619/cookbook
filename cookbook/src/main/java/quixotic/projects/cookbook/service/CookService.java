package quixotic.projects.cookbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import quixotic.projects.cookbook.dto.CookDTO;
import quixotic.projects.cookbook.dto.SignInDTO;
import quixotic.projects.cookbook.dto.SignUpDTO;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.repository.CookRepository;
import quixotic.projects.cookbook.security.JwtTokenProvider;

@RequiredArgsConstructor
@Service
public class CookService {
    private final CookRepository cookRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public CookDTO authenticateCook(SignInDTO loginDto){
        return new CookDTO(
                cookRepository.findByUsername(loginDto.getUsername()).orElseThrow(),
                generateToken(loginDto.getUsername(), loginDto.getPassword())
        );
    }

    public CookDTO createCook(SignUpDTO signUpDTO){
        Cook cook = cookRepository.save(signUpDTO.toCook());

        return new CookDTO(
                cookRepository.save(signUpDTO.toCook()),
                generateToken(cook.getUsername(), cook.getPassword())
        );
    }

    private String generateToken(String username, String password){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        return jwtTokenProvider.generateToken(authentication);
    }
}
