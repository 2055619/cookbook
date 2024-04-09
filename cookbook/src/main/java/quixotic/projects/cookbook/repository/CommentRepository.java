package quixotic.projects.cookbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quixotic.projects.cookbook.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
