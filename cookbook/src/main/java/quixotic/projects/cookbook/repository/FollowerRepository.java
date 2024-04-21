package quixotic.projects.cookbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.model.Follower;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Long> {

    List<Follower> findAllByFollowed(Cook cook);
}
