package quixotic.projects.cookbook.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cookbook.model.enums.Visibility;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public abstract class Publication {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cook cook;
    private LocalDate creationDate;
    @Enumerated(EnumType.STRING)
    private Visibility visibility;
//    @OneToMany(mappedBy = "publication")
//    private Set<Reaction> reactions;
}
