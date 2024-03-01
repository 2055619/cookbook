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
    private String powderUnit;
    private String liquidUnit;
    private String solidUnit;
    private String otherUnit;
    private String role;
    private String token;

    public CookDTO(Cook cook, String token){
        this.username = cook.getUsername();
        this.email = cook.getEmail();
        this.firstName = cook.getFirstname();
        this.lastName = cook.getLastname();
        this.powderUnit = cook.getPowderUnit().name();
        this.liquidUnit = cook.getLiquidUnit().name();
        this.solidUnit = cook.getSolidUnit().name();
        this.otherUnit = cook.getOtherUnit().name();
        this.role = cook.getRole().name();
        this.token = token;
    }
    public CookDTO(Cook cook){
        this.username = cook.getUsername();
        this.email = cook.getEmail();
        this.firstName = cook.getFirstname();
        this.lastName = cook.getLastname();
        this.powderUnit = cook.getPowderUnit().name();
        this.liquidUnit = cook.getLiquidUnit().name();
        this.solidUnit = cook.getSolidUnit().name();
        this.otherUnit = cook.getOtherUnit().name();
        this.role = cook.getRole().name();
    }
}
