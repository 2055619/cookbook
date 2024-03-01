package quixotic.projects.cookbook.exception.badRequestException;

public class RecipeNotFoundException extends BadRequestException {
    public RecipeNotFoundException() {
        super("RecipeNotFound");
    }
}
