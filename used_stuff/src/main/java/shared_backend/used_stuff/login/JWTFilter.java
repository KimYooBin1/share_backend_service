package shared_backend.used_stuff.login;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
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

		//cookie들을 불러온 뒤 Authorization Key에 담긴 쿠키를 찾음
		String authorization = null;
		Cookie[] cookies = request.getCookies();
		//cookies 가 null 일때 index error 발생하기 때문에 따로 처리
		if(cookies != null){
			for (Cookie cookie : cookies) {

				System.out.println(cookie.getName());
				if (cookie.getName().equals("Authorization")) {
					log.info("cookie.getValue = {}", cookie.getValue());
					authorization = cookie.getValue();
				}
			}
		}

		if (authorization == null) {

			System.out.println("token null");
			filterChain.doFilter(request, response);

			//조건이 해당되면 메소드 종료 (필수)
			return;
		}

		log.info("authorization now");

		if (jwtUtil.isExpired(authorization)) {
			log.info("token expired");
			filterChain.doFilter(request, response);

			return;
		}

		//token에서 username과 role 획득
		String username = jwtUtil.getUsername(authorization);
		String role = jwtUtil.getRole(authorization);
		log.info("role = {}", role);


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
