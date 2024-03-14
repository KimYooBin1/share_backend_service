package shared_backend.used_stuff.controller;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shared_backend.used_stuff.dto.JoinRequestDto;
import shared_backend.used_stuff.dto.JoinResponseDto;
import shared_backend.used_stuff.dto.UpdateUserRequest;
import shared_backend.used_stuff.dto.UserResponseDto;
import shared_backend.used_stuff.entity.user.Password;
import shared_backend.used_stuff.entity.user.Profile;
import shared_backend.used_stuff.entity.user.User;
import shared_backend.used_stuff.exception.NotEqualPassword;
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

	/**
	 * admin이 user_id 를 통해 조회
	 */
	@GetMapping("/admin/user/{user_id}")
	public UserResponseDto userDetailTest(@PathVariable("user_id") Long id) {
		User user = userService.findUser(id);
		return new UserResponseDto(user.getPassword(), user.getProfile(), user.getPoint());
	}

	/**
	 * 일반 user는 자신에 대한 상세 정보만 알 수 있ㄷ다.
	 */
	@GetMapping("/user/detail")
	public UserResponseDto userDetail(){
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Password password = (Password)passwordService.loadUserByUsername(name);
		return new UserResponseDto(password, password.getUser().getProfile(), password.getUser().getPoint());
	}

	@PostMapping("/user/edit")
	public UserResponseDto userEdit(@RequestBody @Valid UpdateUserRequest request){
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Password password = (Password)passwordService.loadUserByUsername(name);
		log.info(password.getPassword());
		log.info(passwordEncoder.encode(request.getPassword()));
		if(!passwordEncoder.matches(request.getPassword(), password.getPassword())){
			throw new NotEqualPassword("비밀번호가 다름");
		}
		User user = password.getUser();
		Profile profile = user.getProfile();

		userService.updateUser(password, profile, request);

		return new UserResponseDto(password, profile, user.getPoint());
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
