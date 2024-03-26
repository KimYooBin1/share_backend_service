package shared_backend.used_stuff.login;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shared_backend.used_stuff.dto.user.IdResponse;
import shared_backend.used_stuff.entity.user.Password;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	private final JWTUtil jwtUtil;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

		//클라이언트 요청에서 username, password 추출
		String username = obtainUsername(request);
		String password = obtainPassword(request);


		//스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 함
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);
		//token에 담은 검증을 위한 AuthenticationManager로 전달
		return authenticationManager.authenticate(authToken);
	}

	//로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws
		IOException {
		IdResponse idResponse = new IdResponse(1L);
		// Jackson ObjectMapper를 사용하여 idResponse 객체를 JSON 문자열로 변환합니다.
		String jsonResponse = objectMapper.writeValueAsString(idResponse);
		response.getWriter().write(jsonResponse);
		//password
		Password password = (Password) authentication.getPrincipal();

		String username = password.getUsername();

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		GrantedAuthority auth = iterator.next();

		String role = auth.getAuthority();

		String token = jwtUtil.createJwt(username, role, 60*60*1000L);

		response.addHeader("Authorization", "Bearer " + token);
		// TODO : header 에서 cookie 로 변환
		response.addCookie(createCookie("Authorization", token));
		response.sendRedirect("http://localhost:3000/");
	}

	//로그인 실패시 실행하는 메소드
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
		org.springframework.security.core.AuthenticationException failed) throws IOException, ServletException {
		IdResponse idResponse = new IdResponse(0L);
		String jsonResponse = objectMapper.writeValueAsString(idResponse);
		response.getWriter().write(jsonResponse);
		response.setStatus(401);
	}

	private Cookie createCookie(String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(60 * 60 * 1000);
		// TODO : 실제 배포시에는 https true
		// cookie.setSecure(true);   //local환경이기 때문에 https 불가하기 때문에 지금은 주석처리
		cookie.setPath("/");
		cookie.setHttpOnly(true);

		return cookie;
	}

}
