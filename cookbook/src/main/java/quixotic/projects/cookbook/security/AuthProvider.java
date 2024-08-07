package quixotic.projects.cookbook.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import quixotic.projects.cookbook.exception.forbiddenRequestExceptions.InvalidJwtException;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.repository.CookRepository;
import quixotic.projects.cookbook.exception.badRequestException.UserNotFoundException;


@Component
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final CookRepository cookRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Cook cook = loadUserByUsername(authentication.getPrincipal().toString());
        validateAuthentication(authentication, cook);
        return new UsernamePasswordAuthenticationToken(
                cook.getUsername(),
                cook.getPassword(),
                cook.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private Cook loadUserByUsername(String username) throws UsernameNotFoundException {
        return cookRepository.findCookByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    private void validateAuthentication(Authentication authentication, Cook cook) {
        if (!passwordEncoder.matches(authentication.getCredentials().toString(), cook.getPassword()))
            throw new InvalidJwtException("Incorrect username or password");
    }
}
