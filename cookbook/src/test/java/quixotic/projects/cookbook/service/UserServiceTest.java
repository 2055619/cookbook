package quixotic.projects.cookbook.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import quixotic.projects.cookbook.dto.CookDTO;
import quixotic.projects.cookbook.dto.SignInDTO;
import quixotic.projects.cookbook.dto.SignUpDTO;
import quixotic.projects.cookbook.exception.badRequestException.UsernameTakenException;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.enums.Unit;
import quixotic.projects.cookbook.repository.CookRepository;
import quixotic.projects.cookbook.security.JwtTokenProvider;
import quixotic.projects.cookbook.security.Role;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private CookRepository cookRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    private static final Cook cook = Cook.builder()
            .id(1L)
            .username("testCook")
            .email("asd@asd.com")
            .password("Nonne123!")
            .publications(new HashSet<>())
            .firstName("BlaBla")
            .lastName("BlaBlaLast")
            .powderUnit(Unit.GRAM)
            .liquidUnit(Unit.LITER)
            .solidUnit(Unit.KILOGRAM)
            .otherUnit(Unit.CUP)
            .role(Role.COOK)
            .build();

    SignUpDTO validSignUp = SignUpDTO.builder()
            .username("SignUp")
            .password("Nonne123!")
            .email("email@email.com")
            .firstName("BlaBla")
            .lastName("BlaBlaLast")
            .powderUnit(Unit.GRAM)
            .liquidUnit(Unit.LITER)
            .solidUnit(Unit.KILOGRAM)
            .otherUnit(Unit.CUP)
            .build();
    @Test
    public void authenticateCook_whenCookExists() {
        SignInDTO signInDTO = new SignInDTO();
        signInDTO.setUsername("testCook");
        signInDTO.setPassword("testPassword");

        when(cookRepository.findCookByUsername(signInDTO.getUsername())).thenReturn(Optional.of(cook));
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(jwtTokenProvider.generateToken(any())).thenReturn("testToken");

        CookDTO result = userService.authenticateCook(signInDTO);

        assertEquals("testCook", result.getUsername());
        assertEquals("testToken", result.getToken());
    }

    @Test
    public void authenticateCook_whenCookDoesNotExist() {
        SignInDTO signInDTO = new SignInDTO();
        signInDTO.setUsername("testCook");
        signInDTO.setPassword("testPassword");

        when(cookRepository.findCookByUsername(signInDTO.getUsername())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.authenticateCook(signInDTO));
    }

    @Test
    public void createCook_whenUsernameIsNotTaken() {

        when(cookRepository.save(any())).thenReturn(cook);
        when(passwordEncoder.encode(validSignUp.getPassword())).thenReturn("encodedPassword");
        when(jwtTokenProvider.generateToken(any())).thenReturn("testToken");

        CookDTO result = userService.createCook(validSignUp);

        assertEquals("testCook", result.getUsername());
        assertEquals("testToken", result.getToken());
    }

    @Test
    public void createCook_whenUsernameIsTaken() {
        when(cookRepository.save(any())).thenThrow(new DataIntegrityViolationException(""));

        assertThrows(UsernameTakenException.class, () -> userService.createCook(validSignUp));
    }

    @Test
    public void getMe_whenCookExists() {
        String token = "testToken";
        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn("testCook");
        when(cookRepository.findCookByUsername("testCook")).thenReturn(Optional.of(cook));

        CookDTO result = userService.getMe(token);

        assertEquals("testCook", result.getUsername());
    }

    @Test
    public void getMe_whenCookDoesNotExist() {
        String token = "testToken";
        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn("testCook");
        when(cookRepository.findCookByUsername("testCook")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.getMe(token));
    }
}