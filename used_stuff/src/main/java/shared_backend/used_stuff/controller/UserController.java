package shared_backend.used_stuff.controller;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shared_backend.used_stuff.dto.JoinRequestDto;
import shared_backend.used_stuff.dto.JoinResponseDto;
import shared_backend.used_stuff.entity.Address;
import shared_backend.used_stuff.entity.user.Password;
import shared_backend.used_stuff.entity.user.Profile;
import shared_backend.used_stuff.entity.user.User;
import shared_backend.used_stuff.service.BoardService;
import shared_backend.used_stuff.service.PasswordServiceImpl;
import shared_backend.used_stuff.service.UserService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

	private final PasswordServiceImpl passwordService;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	@PostMapping("/join")
	public JoinResponseDto join(@RequestBody @Valid JoinRequestDto request) {
		Password password = passwordService.createPassword(request, passwordEncoder);
		Profile profile = userService.createProfile(request);
		User user = userService.createUser(password, profile);

		return new JoinResponseDto(user.getPassword(), user.getProfile());
	}


	@GetMapping("/")
	public String checkUser() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Iterator<? extends GrantedAuthority> iter = authorities.iterator();
		GrantedAuthority auth = iter.next();
		return "Main Controller : " + name + " Role : " + auth.getAuthority();
	}
}
