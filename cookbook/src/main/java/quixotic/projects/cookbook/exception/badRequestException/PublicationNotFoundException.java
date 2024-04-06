package quixotic.projects.cookbook.exception.badRequestException;

public class PublicationNotFoundException extends BadRequestException {
    public PublicationNotFoundException() {
        super("message.publicationNotFound");
    }
}
