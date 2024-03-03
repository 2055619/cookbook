package quixotic.projects.cookbook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import quixotic.projects.cookbook.dto.CookDTO;
import quixotic.projects.cookbook.dto.SignInDTO;
import quixotic.projects.cookbook.dto.SignUpDTO;
import quixotic.projects.cookbook.exception.badRequestException.UserNotFoundException;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.enums.Unit;
import quixotic.projects.cookbook.repository.CookRepository;
import quixotic.projects.cookbook.security.JwtAuthenticationEntryPoint;
import quixotic.projects.cookbook.security.JwtTokenProvider;
import quixotic.projects.cookbook.security.Role;
import quixotic.projects.cookbook.security.SecurityConfiguration;
import quixotic.projects.cookbook.service.UserService;

import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig(classes = {AuthController.class, CookbookExceptionHandler.class,
        SecurityConfiguration.class, JwtTokenProvider.class, JwtAuthenticationEntryPoint.class})
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    private final Cook cook = Cook.builder()
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
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private CookRepository cookRepository;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    private ObjectMapper objectMapper;
    private String token = "Tester";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
        when(cookRepository.findByUsername(anyString())).thenReturn(Optional.of(cook));
        this.token = jwtTokenProvider.generateToken(new UsernamePasswordAuthenticationToken("testCook", "Nonne123!"));
    }

    @Test
    public void getMe_AuthorizationHeaderExists() throws Exception {
        when(userService.getMe(token)).thenReturn(new CookDTO(cook));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/cook/auth/me")
                        .header("Authorization", token))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getMe_AuthorizationHeaderDoesNotExist_returnsUnauthorized() throws Exception {
        when(cookRepository.findByUsername(anyString())).thenReturn(Optional.of(cook));

        mockMvc.perform(get("/api/v1/cook/auth/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void authenticateCook_ValidCredentialsProvided_returnsAccepted() throws Exception {
        SignInDTO signInDTO = new SignInDTO();
        signInDTO.setUsername("testCook");
        signInDTO.setPassword("Nonne123!");

        when(userService.authenticateCook(any(SignInDTO.class))).thenReturn(new CookDTO(cook));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/cook/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInDTO)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void authenticateCook_InvalidCredentialsProvided_returnsBadRequest() throws Exception {
        SignInDTO signInDTO = new SignInDTO();
        signInDTO.setUsername("invalidCook");
        signInDTO.setPassword("invalidPassword");

        doThrow(new UserNotFoundException()).when(userService).authenticateCook(any(SignInDTO.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/cook/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void signupCook_ValidSignUpDetailsProvided_returnsAccepted() throws Exception {
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setUsername("newCook");
        signUpDTO.setPassword("NewCook123!");
        signUpDTO.setEmail("newcook@cookbook.com");

        when(userService.createCook(any(SignUpDTO.class))).thenReturn(new CookDTO(cook));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/cook/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDTO)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void signupCook_InvalidSignUpDetailsProvided_returnsBadRequest() throws Exception {
        SignUpDTO signUpDTO = new SignUpDTO();
        signUpDTO.setUsername("newCook");
        signUpDTO.setPassword("invalidPassword");
        signUpDTO.setEmail("invalidEmail");

        when(userService.createCook(any(SignUpDTO.class))).thenThrow(new UserNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/cook/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDTO)))
                .andExpect(status().isBadRequest());
    }

}