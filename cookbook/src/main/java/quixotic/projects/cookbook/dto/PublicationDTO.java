package quixotic.projects.cookbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cookbook.model.Publication;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicationDTO {
    private Long id;
    private String title;
    private String description;
    private String image;
    private String cookUsername;
    private String cookName;
    private String cookAvatar;
    private boolean isPublic;
    private boolean isPublished;
    private boolean isSaved;
    private boolean isLiked;
    private int likes;
    private int saves;
    private int comments;
    private int shares;
    private int views;
    private String createdAt;
    private String updatedAt;


    public PublicationDTO(Publication publication) {
        this.id = publication.getId();
        this.title = publication.getTitle();
        this.description = publication.getDescription();
//        this.image = publication.getImage();
        this.cookUsername = publication.getCook().getUsername();
//        this.cookName = publication.getCook().getName();
//        this.cookAvatar = publication.getCook().getAvatar();
//        this.isPublic = publication.isPublic();
//        this.isPublished = publication.isPublished();
//        this.isSaved = publication.isSaved();
//        this.isLiked = publication.isLiked();
//        this.likes = publication.getLikes();
//        this.saves = publication.getSaves();
//        this.comments = publication.getComments();
//        this.shares = publication.getShares();
//        this.views = publication.getViews();
//        this.createdAt = publication.getCreatedAt().toString();
//        this.updatedAt = publication.getUpdatedAt().toString();
    }
}
