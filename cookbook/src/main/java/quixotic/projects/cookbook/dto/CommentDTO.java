package quixotic.projects.cookbook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cookbook.model.Comment;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDTO {
    private Long id;
    private String content;
    private LocalDateTime creationDate;
    private Long reactionId;

    public CommentDTO(ReactionDTO reactionDTO) {
        this.id = reactionDTO.getComment().getId();
        this.content = reactionDTO.getComment().getContent();
        this.reactionId = reactionDTO.getId();
        this.creationDate = reactionDTO.getComment().getCreationDate();
    }
    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.reactionId = comment.getReaction().getId();
        this.creationDate = comment.getCreationDate();
    }

    public Comment toEntity() {
        return Comment.builder()
                .id(this.id)
                .content(this.content)
                .build();
    }
}
