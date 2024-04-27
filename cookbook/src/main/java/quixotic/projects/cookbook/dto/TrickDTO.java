package quixotic.projects.cookbook.dto;

import lombok.AllArgsConstructor;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.Trick;
import quixotic.projects.cookbook.model.enums.PublicationType;

@AllArgsConstructor
public class TrickDTO extends PublicationDTO {

    public TrickDTO(Trick trick) {
        super(trick);
    }

    public Trick toEntity(Cook cook) {
        return Trick.builder()
                .title(this.getTitle())
                .description(this.getDescription())
                .visibility(this.getVisibility())
                .cook(cook)
                .date(this.getCreationDate())
                .build();
    }
}
