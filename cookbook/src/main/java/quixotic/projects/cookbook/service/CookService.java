package quixotic.projects.cookbook.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import quixotic.projects.cookbook.dto.*;
import quixotic.projects.cookbook.exception.badRequestException.PublicationNotFoundException;
import quixotic.projects.cookbook.exception.badRequestException.RecipeNotFoundException;
import quixotic.projects.cookbook.exception.badRequestException.UserNotFoundException;
import quixotic.projects.cookbook.exception.badRequestException.WrongUserException;
import quixotic.projects.cookbook.model.*;
import quixotic.projects.cookbook.model.enums.Unit;
import quixotic.projects.cookbook.model.summary.UserProfile;
import quixotic.projects.cookbook.repository.*;
import quixotic.projects.cookbook.security.JwtTokenProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CookService {
    private final JwtTokenProvider jwtTokenProvider;
    private final CookRepository cookRepository;
    private final RecipeRepository recipeRepository;
    private final PublicationRepository publicationRepository;
    private final ReactionRepository reactionRepository;
    private final FollowerRepository followerRepository;

    //    Reaction
    public List<ReactionDTO> getReactionsByPublication(Long pubId) {
        Publication publication = publicationRepository.findById(pubId)
                .orElseThrow(PublicationNotFoundException::new);

        return reactionRepository.findAllByPublication(publication).stream()
                .map(ReactionDTO::new)
                .collect(Collectors.toList());
    }

    public ReactionDTO createReaction(ReactionDTO reactionDTO, String token) {
        Cook cook = cookRepository.findCookByUsername(jwtTokenProvider.getUsernameFromJWT(token))
                .orElseThrow(UserNotFoundException::new);
        if (!Objects.equals(cook.getUsername(), reactionDTO.getCookUsername()))
            throw new WrongUserException();
        Publication publication = publicationRepository.findById(reactionDTO.getPublicationId())
                .orElseThrow(PublicationNotFoundException::new);
//        TODO: Ajouter AvgRating
        List<Reaction> reactions = reactionRepository.findAllByPublication(publication);
        reactions.add(reactionDTO.toEntity(cook, publication));
        publication.calculateAvgRating(reactions);

        return new ReactionDTO(reactionRepository.save(reactionDTO.toEntity(cook, publication)));
    }

    //    User
    public UserProfile getUserProfile(String username) {
        return cookRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    public CookDTO updateUserProfile(CookDTO cookDTO, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);

        Cook cook = cookRepository.findCookByUsername(username).orElseThrow(UserNotFoundException::new);

        cook.setEmail(cookDTO.getEmail());
        cook.setFirstName(cookDTO.getFirstName());
        cook.setLastName(cookDTO.getLastName());
        cook.setPowderUnit(Unit.valueOf(cookDTO.getPowderUnit()));
        cook.setLiquidUnit(Unit.valueOf(cookDTO.getLiquidUnit()));
        cook.setSolidUnit(Unit.valueOf(cookDTO.getSolidUnit()));
        cook.setOtherUnit(Unit.valueOf(cookDTO.getOtherUnit()));

        return new CookDTO(cookRepository.save(cook));
    }

    public FollowerDTO followCook(String usernameToFollow, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        Cook follower = cookRepository.findCookByUsername(username).orElseThrow(UserNotFoundException::new);
        Cook followed = cookRepository.findCookByUsername(usernameToFollow).orElseThrow(UserNotFoundException::new);

        if (followerRepository.findByFollowedAndFollower(followed, follower).isPresent()){
            throw new IllegalArgumentException("message.cookFollowed");
        }

        return new FollowerDTO(followerRepository.save(Follower.builder().followed(followed).follower(follower).build()));
    }

    public CookDTO unfollowCook(String usernameToUnfollow, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);

        Follower follower = followerRepository.findByFollowedAndFollower(
                        cookRepository.findCookByUsername(usernameToUnfollow).orElseThrow(UserNotFoundException::new),
                        cookRepository.findCookByUsername(username).orElseThrow(UserNotFoundException::new))
                .orElseThrow(UserNotFoundException::new);

        followerRepository.delete(follower);
        return new CookDTO(follower.getFollower());

    }

    public List<CookDTO> getFollowers(String username) {
        Cook cook = cookRepository.findCookByUsername(username).orElseThrow(UserNotFoundException::new);

        List<Follower> followers = followerRepository.findAllByFollowed(cook);
        return followers.stream().map((follower -> new CookDTO(follower.getFollower()))).toList();
    }

    public List<CookDTO> getFollowing(String username) {
        Cook cook = cookRepository.findCookByUsername(username).orElseThrow(UserNotFoundException::new);

        List<Follower> followers = followerRepository.findAllByFollower(cook);
        return followers.stream().map((follower -> new CookDTO(follower.getFollowed()))).toList();
    }

    private List<PublicationDTO> filterPublicationsByVisibility(List<Publication> publications, Cook user) {
        return publications.stream()
                .filter(publication -> switch (publication.getVisibility()) {
                    case PUBLIC -> true;
                    case FOLLOWERS -> user.equals(publication.getCook()) || followerRepository.findByFollowedAndFollower(publication.getCook(), user).isPresent();
                    case FRIENDS -> user.equals(publication.getCook()) || followerRepository.findByFollowedAndFollower(publication.getCook(), user).isPresent() &&
                            followerRepository.findByFollowedAndFollower(user, publication.getCook()).isPresent();
                    case SECRET -> user.equals(publication.getCook());
                })
                .map((publication -> switch (publication.getPublicationType()) {
                    case RECIPE -> {
                        if (publication instanceof Recipe) {
                            yield new RecipeDTO((Recipe) publication);
                        } else {
                            yield recipeRepository.findByTitle(publication.getTitle())
                                    .map(RecipeDTO::new)
                                    .orElseThrow(RecipeNotFoundException::new);

                        }
                    }
                    case TRICK -> {
                        if (publication instanceof Trick) {
                            yield new TrickDTO((Trick) publication);
                        }
                        yield new PublicationDTO(publication);
                    }
                })).collect(Collectors.toList());
    }

    private List<RecipeDTO> filterRecipesByVisibility(List<Recipe> recipes, Cook user) {
        return recipes.stream()
                .filter(recipe -> switch (recipe.getVisibility()) {
                    case PUBLIC -> true;
                    case FOLLOWERS -> user.equals(recipe.getCook()) || followerRepository.findByFollowedAndFollower(recipe.getCook(), user).isPresent();
                    case FRIENDS -> user.equals(recipe.getCook()) || followerRepository.findByFollowedAndFollower(recipe.getCook(), user).isPresent() &&
                            followerRepository.findByFollowedAndFollower(user, recipe.getCook()).isPresent();
                    case SECRET -> user.equals(recipe.getCook());
                })
                .map(RecipeDTO::new)
                .collect(Collectors.toList());
    }
}
