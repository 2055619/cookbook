package quixotic.projects.cookbook.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cookbook.model.enums.DifficultyLevel;
import quixotic.projects.cookbook.model.enums.Visibility;

import java.time.LocalDate;
import java.util.Set;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public abstract class Publication {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cook author;
    private LocalDate creationDate;
    @Enumerated(EnumType.STRING)
    private Visibility visibility;
    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficulty;

//    @OneToMany(mappedBy = "publication")
//    private Set<Reaction> reactions;
}
