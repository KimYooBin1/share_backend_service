package shared_backend.used_stuff.login;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import shared_backend.used_stuff.dto.oauth2.CustomOAuth2User;

@Component
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final JWTUtil jwtUtil;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		CustomOAuth2User customOAuth2User = (CustomOAuth2User)authentication.getPrincipal();

		String username = customOAuth2User.getUsername();

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
		GrantedAuthority auth = iterator.next();
		String role = auth.getAuthority();

		String token = jwtUtil.createJwt(username, role, 60*60*1000L);
		response.addCookie(createCookie("Authorization", token));
		response.sendRedirect("http://localhost:3000/");
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
