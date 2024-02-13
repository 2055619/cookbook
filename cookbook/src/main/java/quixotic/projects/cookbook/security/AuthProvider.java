package quixotic.projects.cookbook.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import quixotic.projects.cookbook.exception.badRequestException.InvalidJwtException;
import quixotic.projects.cookbook.model.User;
import quixotic.projects.cookbook.repository.UserRepository;
import quixotic.projects.cookbook.exception.goneRequestException.UserNotFoundException;


@Component
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider{
	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		User user = loadUserByEmail(authentication.getPrincipal().toString());
		validateAuthentication(authentication, user);
		return new UsernamePasswordAuthenticationToken(
			user.getEmail(),
			user.getPassword(),
			user.getAuthorities()
		);
	}

	@Override
	public boolean supports(Class<?> authentication){
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

	private User loadUserByEmail(String email) throws UsernameNotFoundException{
		return userRepository.findByUsername(email)
			.orElseThrow(UserNotFoundException::new);
	}

	private void validateAuthentication(Authentication authentication, User user){
		if(!passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword()))
			throw new InvalidJwtException("Incorrect username or password");
	}
}
