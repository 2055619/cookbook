package quixotic.projects.cookbook.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private float rating = -1;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Comment comment;
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Cook cook;
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Publication publication;
}
