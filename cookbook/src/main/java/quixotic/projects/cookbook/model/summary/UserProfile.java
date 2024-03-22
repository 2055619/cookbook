package quixotic.projects.cookbook.model.summary;

import quixotic.projects.cookbook.model.enums.Unit;

public interface UserProfile {
    String getUsername();
    String getEmail();
    String getFirstName();
    String getLastName();
    Unit getPowderUnit();
    Unit getLiquidUnit();
    Unit getSolidUnit();
    Unit getOtherUnit();
}
