package quixotic.projects.cookbook.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cookbook.model.enums.Unit;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Ingrediant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private float amount;
    private Unit unit;

    @ManyToOne
    private Recipe recipe;
}
