package quixotic.projects.cookbook.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import quixotic.projects.cookbook.dto.CookDTO;
import quixotic.projects.cookbook.dto.SignInDTO;
import quixotic.projects.cookbook.dto.SignUpDTO;
import quixotic.projects.cookbook.exception.badRequestException.UsernameTakenException;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.repository.CookRepository;
import quixotic.projects.cookbook.security.JwtTokenProvider;
import quixotic.projects.cookbook.security.Role;
import quixotic.projects.cookbook.validation.Validation;

@Service
@RequiredArgsConstructor
public class UserService {
    private final CookRepository cookRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public CookDTO authenticateCook(SignInDTO loginDto) {
        return new CookDTO(
                cookRepository.findByUsername(loginDto.getUsername()).orElseThrow(),
                generateToken(loginDto.getUsername(), loginDto.getPassword())
        );
    }
    public CookDTO createCook(SignUpDTO signUpDTO) {
        Validation.validateSignIn(signUpDTO);
        Cook cook;
        try {
            cook = cookRepository.save(
                    Cook.builder()
                            .username(signUpDTO.getUsername())
                            .password(passwordEncoder.encode(signUpDTO.getPassword()))
                            .email(signUpDTO.getEmail())
                            .firstname(signUpDTO.getFirstName())
                            .lastname(signUpDTO.getLastName())
                            .powderUnit(signUpDTO.getPowderUnit())
                            .liquidUnit(signUpDTO.getLiquidUnit())
                            .solidUnit(signUpDTO.getSolidUnit())
                            .otherUnit(signUpDTO.getOtherUnit())
                            .role(Role.COOK)
                            .build()
            );
        } catch (DataIntegrityViolationException e) {
            System.out.println("Error: " + e);
            throw new UsernameTakenException();
        }


        String token = generateToken(cook.getUsername(), signUpDTO.getPassword());
        return new CookDTO(cook, token);
    }
    private String generateToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        return jwtTokenProvider.generateToken(authentication);
    }

    public CookDTO getMe(String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        return new CookDTO(cookRepository.findByUsername(username).orElseThrow());
    }
}