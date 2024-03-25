package shared_backend.used_stuff.login;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shared_backend.used_stuff.service.CustomOAuth2UserService;

@Configuration
@Slf4j
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

	private final AuthenticationConfiguration authenticationConfiguration;
	private final JWTUtil jwtUtil;
	private final CustomOAuth2UserService customOAuth2UserService;
	private final CustomSuccessHandler customSuccessHandler;
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors((corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
				@Override
				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

					CorsConfiguration configuration = new CorsConfiguration();

					configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
					configuration.setAllowedMethods(Collections.singletonList("*"));
					configuration.setAllowCredentials(true);
					configuration.setAllowedHeaders(Collections.singletonList("*"));
					configuration.setMaxAge(3600L);

					configuration.setExposedHeaders(Collections.singletonList("Authorization"));

					return configuration;
				}
			})));
		http
			.csrf(AbstractHttpConfigurer::disable)    //api 서버의 경우 session 이 stateless 로 관리 되기 때문에 disable 로 해도 된다
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(Customizer.withDefaults())


			.oauth2Login((oauth2) -> oauth2
				.userInfoEndpoint((userInfoEndpointConfig -> userInfoEndpointConfig
					.userService(customOAuth2UserService)))
				.successHandler(customSuccessHandler))


			// .httpBasic(AbstractHttpConfigurer::disable) header 에 값을 받아온다? mvc 에서 보안적인 측면으로 활용
			.authorizeHttpRequests((auth) -> auth
				.requestMatchers("/", "/login", "/join", "/boards/**", "/comments/**").permitAll()
				.requestMatchers("/admin").hasRole("ADMIN")
				.anyRequest().authenticated())    //위에서 설정해둔 주소 의외의 모든 주소
			.addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

		//필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
		http
			.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class)
			.sessionManagement((session) -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// 다중 로그인 처리, session 으로 처리하는거라 jwt 와 연관 없는거 같음
		// http
		// 	.sessionManagement((auth) -> auth
		// 		.maximumSessions(1)
		// 		.maxSessionsPreventsLogin(true)
		// 	);

		return http.build();
	}
}

