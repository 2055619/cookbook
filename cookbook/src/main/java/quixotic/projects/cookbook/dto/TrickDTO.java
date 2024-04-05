package quixotic.projects.cookbook.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.Trick;

@AllArgsConstructor
public class TrickDTO extends PublicationDTO {

    public TrickDTO(Trick trick) {
        super(trick);
    }

    public Trick toEntity(Cook cook) {
        return new Trick(this.getId(), this.getTitle(), this.getDescription(), cook, this.getCreationDate(), this.getVisibility());
    }
}
