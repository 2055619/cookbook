package quixotic.projects.cookbook.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import quixotic.projects.cookbook.model.Cook;
import quixotic.projects.cookbook.repository.CookRepository;
import quixotic.projects.cookbook.exception.goneRequestException.UserNotFoundException;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter{
	private final JwtTokenProvider tokenProvider;
	private final CookRepository cookRepository;

	public JwtAuthenticationFilter(JwtTokenProvider tokenProvider, CookRepository userRepository){
		this.tokenProvider = tokenProvider;
		this.cookRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
	)throws ServletException, IOException{
		String token = getJWTFromRequest(request);
		if(StringUtils.hasText(token)){
			try{
				tokenProvider.validateToken(token);
				String username = tokenProvider.getUsernameFromJWT(token);
				Cook cook = cookRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						cook.getUsername(), null, cook.getAuthorities()
				);
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}catch(Exception e){
				logger.error("Could not set user authentication in security context", e);
			}
		}
		filterChain.doFilter(request, response);
	}

	private String getJWTFromRequest(HttpServletRequest request){
		return request.getHeader("Authorization");
	}

}
