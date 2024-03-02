package quixotic.projects.cookbook.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import quixotic.projects.cookbook.model.summary.RecipeSummary;
import quixotic.projects.cookbook.service.CookService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CookControllerTest {

    @InjectMocks
    private CookController cookController;

    @Mock
    private CookService cookService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cookController).build();
    }

    @Test
    public void getRecipesByTitle_returnsExpectedResponse() throws Exception {
        String title = "testTitle";
        List<RecipeSummary> recipeSummaries = List.of();
        when(cookService.getRecipesByTitle(title)).thenReturn(recipeSummaries);

        mockMvc.perform(get("/api/v1/cook/recipes/title").param("title", title))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}