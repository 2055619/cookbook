package quixotic.projects.cookbook.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "follower_id")
    private Cook follower;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "followed_id")
    private Cook followed;
}
