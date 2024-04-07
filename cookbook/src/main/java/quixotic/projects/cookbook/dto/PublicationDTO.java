package quixotic.projects.cookbook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cookbook.model.Publication;
import quixotic.projects.cookbook.model.enums.Visibility;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicationDTO {
    private Long id;
    private String title;
    private String description;
    private String cookUsername;
    private LocalDate creationDate;
    private Visibility visibility;
    private float averageRating;

    public PublicationDTO(Publication publication) {
        this.id = publication.getId();
        this.title = publication.getTitle();
        this.description = publication.getDescription();
        this.cookUsername = publication.getCook().getUsername();
        this.creationDate = publication.getCreationDate();
        this.visibility = publication.getVisibility();
        this.averageRating = publication.getAverageRating();
    }
}
