package quixotic.projects.cookbook.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
    @Column(unique = true, nullable = false)
    private String title;
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    private Cook cook;
    private LocalDate creationDate = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    public Publication(String title, String description, Visibility visibility, Cook cook) {
        this.title = title;
        this.description = description;
        this.visibility = visibility;
        this.cook = cook;
    }
//    @OneToMany(mappedBy = "publication")
//    private Set<Reaction> reactions;
}
