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
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.enums.Unit;
import quixotic.projects.cookbook.repository.CookRepository;
import quixotic.projects.cookbook.security.*;
import quixotic.projects.cookbook.service.UserService;

import java.util.HashSet;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
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
    public void getMe_whenAuthorizationHeaderExists() throws Exception {
        when(userService.getMe(token)).thenReturn(new CookDTO(cook));
        when(cookRepository.findByUsername(anyString())).thenReturn(Optional.of(cook));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/cook/auth/me")
                        .header("Authorization", token))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getMe_whenAuthorizationHeaderDoesNotExist_returnsUnauthorized() throws Exception {
        when(cookRepository.findByUsername(anyString())).thenReturn(Optional.of(cook));

        mockMvc.perform(get("/api/v1/cook/auth/me"))
                .andExpect(status().isUnauthorized());
    }
}