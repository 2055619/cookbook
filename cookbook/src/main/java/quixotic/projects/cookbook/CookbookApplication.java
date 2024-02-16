package quixotic.projects.cookbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import quixotic.projects.cookbook.dto.SignUpDTO;
import quixotic.projects.cookbook.model.enums.Unit;
import quixotic.projects.cookbook.service.CookService;

@SpringBootApplication
public class CookbookApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(CookbookApplication.class, args);
    }

    @Autowired
    private CookService cookService;

    @Override
    public void run(String... args) throws Exception {
        cookService.createCook(
                SignUpDTO.builder()
                        .username("TheChef")
                        .password("Password123")
                        .email("qwe@qwe.com")
                        .firstName("John")
                        .lastName("Doe")
                        .powderUnit(Unit.CUP)
                        .liquidUnit(Unit.CUP)
                        .solidUnit(Unit.GRAM)
                        .otherUnit(Unit.CUP)
                        .build()
        );
    }
}
