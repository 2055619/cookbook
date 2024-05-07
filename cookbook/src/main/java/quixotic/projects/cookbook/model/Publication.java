package quixotic.projects.cookbook.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import quixotic.projects.cookbook.model.enums.PublicationType;
import quixotic.projects.cookbook.model.enums.Visibility;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false, length = 500)
    private String title;
    @Column(length = 1000)
    private String description;
    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    private Cook cook;
    private LocalDate creationDate = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private Visibility visibility;
    private float averageRating = 0;
    @Enumerated(EnumType.STRING)
    private PublicationType publicationType;
    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Reaction> reactions = new HashSet<>();

    public Publication(String title, String description, Visibility visibility, Cook cook, PublicationType publicationType) {
        this.title = title;
        this.description = description;
        this.visibility = visibility;
        this.cook = cook;
        this.publicationType = publicationType;
    }

    public void addReaction(Reaction reaction) {
        reactions.add(reaction);
//        recalculateAverageRating();
    }

    public void removeReaction(Reaction reaction) {
        reactions.remove(reaction);
        reaction.setPublication(null);
        recalculateAverageRating();
    }

    public void recalculateAverageRating() {
        if (reactions.isEmpty()) {
            averageRating = 0;
        } else {
            float sum = 0;
            for (Reaction reaction : reactions) {
                if (reaction.getRating() != -1){
                    sum += reaction.getRating();
                }
            }
            averageRating = sum / reactions.size();
        }
    }

    public void calculateAvgRating(List<Reaction> reactions) {
        if (reactions.isEmpty()) {
            averageRating = 0;
        } else {
            float sum = 0;
            for (Reaction reaction : reactions) {
                if (reaction.getRating() != -1){
                    sum += reaction.getRating();
                }
            }
            averageRating = sum / reactions.size();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
