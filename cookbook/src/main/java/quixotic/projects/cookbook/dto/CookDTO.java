package quixotic.projects.cookbook.dto;

import lombok.*;
import quixotic.projects.cookbook.model.Cook;

import java.util.List;

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
//    List<CookDTO> followers;

    public CookDTO(Cook cook, String token){
        this.username = cook.getUsername();
        this.email = cook.getEmail();
        this.firstName = cook.getFirstName();
        this.lastName = cook.getLastName();
        this.powderUnit = cook.getPowderUnit().name();
        this.liquidUnit = cook.getLiquidUnit().name();
        this.solidUnit = cook.getSolidUnit().name();
        this.otherUnit = cook.getOtherUnit().name();
        this.role = cook.getRole().name();
        this.token = token;
//        if (cook.getFollowers() != null) {
//            this.followers = cook.getFollowers().stream().map((follower -> new CookDTO(follower.getFollowed()))).toList();
//        }
    }

    public CookDTO(Cook cook){
        this.username = cook.getUsername();
        this.email = cook.getEmail();
        this.firstName = cook.getFirstName();
        this.lastName = cook.getLastName();
        this.powderUnit = cook.getPowderUnit().name();
        this.liquidUnit = cook.getLiquidUnit().name();
        this.solidUnit = cook.getSolidUnit().name();
        this.otherUnit = cook.getOtherUnit().name();
        this.role = cook.getRole().name();
//        if (cook.getFollowers() != null) {
//            System.out.println("Followers: " + cook.getFollowers());
//            this.followers = cook.getFollowers().stream().map((follower -> new CookDTO(follower.getFollowed()))).toList();
//        }
    }
}
