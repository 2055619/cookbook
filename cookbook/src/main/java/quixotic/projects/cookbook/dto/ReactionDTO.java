package quixotic.projects.cookbook.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.Publication;
import quixotic.projects.cookbook.model.Reaction;

import java.util.List;

import static quixotic.projects.cookbook.validation.Validation.validateReaction;

@Data
@NoArgsConstructor
public class ReactionDTO {
    private Long id;
    private float rating = -1;
    private CommentDTO comment;
    private String cookUsername;
    private Long publicationId;

    public ReactionDTO(Reaction reaction) {
        this.id = reaction.getId();
        this.rating = reaction.getRating();
        this.cookUsername = reaction.getCook().getUsername();
        this.publicationId = reaction.getPublication().getId();
        this.comment = new CommentDTO(this);
    }

    public Reaction toEntity(Cook cook, Publication publication) {
        validateReaction(this);
        return Reaction.builder()
                .id(this.id)
                .rating(Math.round(this.rating * 4) / 4.0f)
                .cook(cook)
                .publication(publication)
                .comment(this.comment.toEntity())
                .build();
    }

    public void setRating(float rating) {
        validateReaction(this);
        this.rating = Math.round(rating * 4) / 4.0f;
    }
}
