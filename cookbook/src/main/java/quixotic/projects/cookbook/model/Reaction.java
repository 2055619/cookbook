package quixotic.projects.cookbook.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String comment;
    @ManyToOne
    @JoinColumn(name = "cook_id", nullable = false)
    private Cook cook;
    @ManyToOne
    @JoinColumn(name = "publication_id", nullable = false)
    private Publication publication;
}
