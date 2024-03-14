package quixotic.projects.cookbook.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
public enum Unit {
    TEASPOON(1, 0.333333f, 0.0208333f, 0.166667f, 0.00520833f, 4.92892f, 0.00492892f, 4.92892f, 0.00492892f, 0.0208333f, 0.166667f, 0.0208333f, 0.0208333f),
    TABLESPOON(3, 1, 0.0625f, 0.5f, 0.015625f, 14.7868f, 0.0147868f, 14.7868f, 0.0147868f, 0.0625f, 0.5f, 0.0625f, 0.0625f),
    CUP(48, 16, 1, 8, 0.25f, 236.588f, 0.236588f, 236.588f, 0.236588f, 1, 8, 1, 1),
    OUNCE(6, 2, 0.125f, 1, 0.03125f, 29.5735f, 0.0295735f, 29.5735f, 0.0295735f, 0.125f, 1, 0.125f, 0.125f),
    POUND(96, 32, 2, 16, 1, 453.592f, 0.453592f, 453.592f, 0.453592f, 2, 16, 2, 2),
    GRAM(0.202884f, 0.067628f, 0.00422675f, 0.033814f, 0.00105669f, 1, 0.001f, 1, 0.001f, 0.00422675f, 0.033814f, 0.00422675f, 0.00422675f),
    KILOGRAM(202.884f, 67.628f, 4.22675f, 33.814f, 1.05669f, 1000, 1, 1000, 1, 4.22675f, 33.814f, 4.22675f, 4.22675f),
    MILLILITER(0.202884f, 0.067628f, 0.00422675f, 0.033814f, 0.00105669f, 1, 0.001f, 1, 0.001f, 0.00422675f, 0.033814f, 0.00422675f, 0.00422675f),
    LITER(202.884f, 67.628f, 4.22675f, 33.814f, 1.05669f, 1000, 1, 1000, 1, 4.22675f, 33.814f, 4.22675f, 4.22675f),
    PINCH(0.0208333f, 0.00694444f, 0.000434722f, 0.00347222f, 0.000108798f, 0.102292f, 0.000102292f, 0.102292f, 0.000102292f, 1, 0.00347222f, 1, 1),
    DASH(0.0625f, 0.0208333f, 0.00130208f, 0.0104167f, 0.00032552f, 0.308877f, 0.000308877f, 0.308877f, 0.000308877f, 0.0104167f, 0.0833333f, 0.0104167f, 0.0104167f),
    EACH(0.0625f, 0.0208333f, 0.00130208f, 0.0104167f, 0.00032552f, 0.308877f, 0.000308877f, 0.308877f, 0.000308877f, 0.0104167f, 0.0833333f, 0.0104167f, 0.0104167f),
    NUMBER(0.0625f, 0.0208333f, 0.00130208f, 0.0104167f, 0.00032552f, 0.308877f, 0.000308877f, 0.308877f, 0.000308877f, 0.0104167f, 0.0833333f, 0.0104167f, 0.0104167f),
    ;

    private final float teaspoon;
    private final float tablespoon;
    private final float cup;
    private final float ounce;
    private final float pound;
    private final float gram;
    private final float kilogram;
    private final float milliliter;
    private final float liter;
    private final float pinch;
    private final float dash;
    private final float each;
    private final float number;

    @Override
    public String toString() {
        return this.name() + '{' +
                "teaspoon=" + teaspoon +
                ", tablespoon=" + tablespoon +
                ", cup=" + cup +
                ", ounce=" + ounce +
                ", pound=" + pound +
                ", gram=" + gram +
                ", kilogram=" + kilogram +
                ", milliliter=" + milliliter +
                ", liter=" + liter +
                ", pinch=" + pinch +
                ", dash=" + dash +
                ", each=" + each +
                ", number=" + number +
                '}';
    }


    //    @Override
//    public String toString() {
//        return this.name() ;
//    }
}
