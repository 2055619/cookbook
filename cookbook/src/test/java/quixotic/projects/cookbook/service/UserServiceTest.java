package quixotic.projects.cookbook.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import quixotic.projects.cookbook.dto.CookDTO;
import quixotic.projects.cookbook.dto.SignInDTO;
import quixotic.projects.cookbook.dto.SignUpDTO;
import quixotic.projects.cookbook.exception.badRequestException.UsernameTakenException;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.repository.CookRepository;
import quixotic.projects.cookbook.security.JwtTokenProvider;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

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

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void authenticateCook_whenCookExists() {
        SignInDTO signInDTO = new SignInDTO();
        signInDTO.setUsername("testCook");
        signInDTO.setPassword("testPassword");

        when(cookRepository.findByUsername(signInDTO.getUsername())).thenReturn(Optional.of(new Cook()));
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

        when(cookRepository.findByUsername(signInDTO.getUsername())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.authenticateCook(signInDTO));
    }

    @Test
    public void createCook_whenUsernameIsNotTaken() {
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setUsername("testCook");
        signUpDTO.setPassword("testPassword");

        when(cookRepository.save(any())).thenReturn(new Cook());
        when(passwordEncoder.encode(signUpDTO.getPassword())).thenReturn("encodedPassword");
        when(jwtTokenProvider.generateToken(any())).thenReturn("testToken");

        CookDTO result = userService.createCook(signUpDTO);

        assertEquals("testCook", result.getUsername());
        assertEquals("testToken", result.getToken());
    }

    @Test
    public void createCook_whenUsernameIsTaken() {
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setUsername("testCook");
        signUpDTO.setPassword("testPassword");

        when(cookRepository.save(any())).thenThrow(new DataIntegrityViolationException(""));

        assertThrows(UsernameTakenException.class, () -> userService.createCook(signUpDTO));
    }

    @Test
    public void getMe_whenCookExists() {
        String token = "testToken";
        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn("testCook");
        when(cookRepository.findByUsername("testCook")).thenReturn(Optional.of(new Cook()));

        CookDTO result = userService.getMe(token);

        assertEquals("testCook", result.getUsername());
    }

    @Test
    public void getMe_whenCookDoesNotExist() {
        String token = "testToken";
        when(jwtTokenProvider.getUsernameFromJWT(token)).thenReturn("testCook");
        when(cookRepository.findByUsername("testCook")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.getMe(token));
    }
}