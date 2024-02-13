package quixotic.projects.cookbook.dto;

import lombok.*;
import quixotic.projects.cookbook.model.Cook;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CookDTO {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String preferedUnit;
    private String role;
    private String token;

    public CookDTO(Cook cook, String token){
        this.username = cook.getUsername();
        this.email = cook.getEmail();
        this.firstName = cook.getFirstname();
        this.lastName = cook.getLastname();
        this.preferedUnit = cook.getPreferedUnit().name();
        this.role = cook.getRole().name();
        this.token = token;
    }
}
