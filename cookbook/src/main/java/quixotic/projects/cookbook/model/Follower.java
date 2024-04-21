package quixotic.projects.cookbook.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Cook follower;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Cook followed;

    @Override
    public String toString() {
        if (follower == null || followed == null) {
            return "id: " + id + ", NULLs";
        }
        return "id: " + id +
                ", follower: " + follower.getUsername() +
                ", followed: " + followed.getUsername();
    }
}
