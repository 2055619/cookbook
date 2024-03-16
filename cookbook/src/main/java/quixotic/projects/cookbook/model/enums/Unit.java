package quixotic.projects.cookbook.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
public enum Unit {
    TEASPOON(1, 0.333333333333333333333333333333333333333333333333333333f, 0.0208333f, 0.166667f, 0.0105f, 5.69f, 0.004928921594f, 4.92892f, 0.004929f, 16, 8, 1),
    TABLESPOON(3, 1, 0.0625f, 0.5f, 0.03125f, 14.7868f, 0.125f, 14.7868f, 0.0147868f, 48, 24, 3),
    CUP(48, 16, 1, 8.11537f, 0.0625f, 236.588f, 0.236588f, 236.588f, 0.24f, 192, 384, 48),
    OUNCE(6, 2, 0.123224f, 1, 0.0625f, 29.5735f, 0.0295735f, 29.5735f, 0.0295735f, 96, 48, 6),
    POUND(95.2f, 31.7333333f, 1.9835f, 16, 1, 453.592f, 0.453592f, 453.592f, 0.453592f, 1824, 912, 95.2f),
    GRAM(0.1756515f, 0.0585505f, 0.00366f, 0.02835f, 0.0022f, 1, 0.001f, 1.0416667f, 0.001042f, 3.333f, 1.666f, 0.208333333f),
    KILOGRAM(384, 128, 8, 64, 4, 1000, 1, 1000, 1, 3200, 1600, 200),
    MILLILITER(0.202884f, 0.067628f, 0.00422f, 0.033814f, 0.0021f, 0.03527f, 0.001f, 1, 0.001f, 3.218f, 1.609f, 0.202884f),
    LITER(202.884f, 67.628f, 4.167f, 33.814f, 1.05669f, 1000, 1, 1000, 1, 3200, 1600, 200),
    PINCH(0.203f, 0.0676667f, 0.00421946f, 0.033814f, 0.0021f, 0.035274f, 0.0000439142f, 0.0520417f, 0.000052f, 1, 0.5f, 0.0625f),
    DASH(0.1015f, 0.0416666666666667f, 0.0026041666666667f, 0.0208333333333333f, 0.00105f, 0.017637f, 0.0000219571f, 0.0260209f, 0.000625f, 8, 1, 0.125f),
    NUMBER(0.0625f, 0.0208333f, 0.333333f, 0.0105042016806723f, 0.0105f, 0.005f, 0.0005f, 4.92610837438423f, 1000, 16, 8, 1);
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
                ", number=" + number +
                '}';
    }
}
