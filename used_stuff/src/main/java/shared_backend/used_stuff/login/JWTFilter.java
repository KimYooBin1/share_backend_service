package shared_backend.used_stuff.login;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shared_backend.used_stuff.entity.user.Password;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
	private final JWTUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String authorization = request.getHeader("Authorization");

		if (authorization == null || !authorization.startsWith("Bearer")) {
			log.info("token null");
			filterChain.doFilter(request, response);

			return;
		}
		log.info("authorization now");
		//authorization 에서 Bearer 부분 제거
		String token = authorization.split(" ")[1];
		if (jwtUtil.isExpired(token)) {
			log.info("token expired");
			filterChain.doFilter(request, response);

			return;
		}

		//token에서 username과 role 획득
		String username = jwtUtil.getUsername(token);
		String role = jwtUtil.getRole(token);

		//Password를 생성하여 값 set
		Password password = new Password(username, "temppassword", role);
		// Authentication authToken = new UsernamePasswordAuthenticationToken(password, null, password.getAuthorities());
		//세션에 사용자 등록

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
			password, null, password.getAuthorities());

		log.info("authToken = {}", authToken);
		SecurityContextHolder.getContext().setAuthentication(authToken);

		filterChain.doFilter(request, response);
	}
}
