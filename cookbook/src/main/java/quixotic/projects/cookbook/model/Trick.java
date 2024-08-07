package quixotic.projects.cookbook.model;

import jakarta.persistence.Entity;
import lombok.*;
import quixotic.projects.cookbook.model.enums.PublicationType;
import quixotic.projects.cookbook.model.enums.Visibility;

import java.time.LocalDate;
import java.util.Objects;

//@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Trick extends Publication {
//    private String test;

    public Trick(String title, String description, Visibility visibility, Cook cook) {
        super(title, description, visibility, cook, PublicationType.TRICK);
    }
    @Builder
    public Trick(Long id, String title, String description, Cook cook, LocalDate date, Visibility visibility) {
        super(title, description, visibility, cook, PublicationType.TRICK);

    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}