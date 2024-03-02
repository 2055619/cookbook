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
public class SignUpDTO {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Unit powderUnit;
    private Unit liquidUnit;
    private Unit solidUnit;
    private Unit otherUnit;

    public Cook toCook() {
        Validation.validateSignIn(this);
        return Cook.builder()
                .username(username)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .powderUnit(powderUnit)
                .liquidUnit(liquidUnit)
                .solidUnit(solidUnit)
                .otherUnit(otherUnit)
                .role(Role.COOK)
                .build();
    }
}
