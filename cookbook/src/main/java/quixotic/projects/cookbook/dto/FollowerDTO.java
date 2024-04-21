package quixotic.projects.cookbook.dto;

import quixotic.projects.cookbook.model.Follower;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowerDTO {
    private CookDTO follower;
    private CookDTO followed;

    public FollowerDTO(Follower follower) {
        this.follower = new CookDTO(follower.getFollower());
        this.followed = new CookDTO(follower.getFollowed());
    }
}
