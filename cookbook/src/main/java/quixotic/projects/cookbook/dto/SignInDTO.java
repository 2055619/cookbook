package quixotic.projects.cookbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.enums.Unit;
import quixotic.projects.cookbook.security.Role;
import quixotic.projects.cookbook.validation.Validation;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignInDTO {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Unit preferedUnit;

    public Cook toCook() {
        Validation.validateSignIn(this);
        return Cook.builder()
                .username(username)
                .password(password)
                .email(email)
                .firstname(firstName)
                .lastname(lastName)
                .preferedUnit(preferedUnit)
                .role(Role.COOK)
                .build();
    }
}
