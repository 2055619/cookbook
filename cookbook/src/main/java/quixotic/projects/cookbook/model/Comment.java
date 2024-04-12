package quixotic.projects.cookbook.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 1000)
    private String content;
    private final LocalDateTime creationDate = LocalDateTime.now();
    @ToString.Exclude
    @OneToOne(cascade = CascadeType.PERSIST)
    private Reaction reaction;

//    @ToString.Exclude
//    @ManyToOne
//    private Comment parentComment = null;
//    @ToString.Exclude
//    @ManyToMany
//    private Set<Comment> replies = new HashSet<>();

    public Comment(String content) {
        this.content = content;
    }
}
