package quixotic.projects.cookbook.model;

import jakarta.persistence.*;

@Entity
public class Reaction {
    @Id
    @GeneratedValue
    private Long id;
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Publication publication;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cook cook;
    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;

    public enum ReactionType {
        MIAM,
        EWW,
    }
}
