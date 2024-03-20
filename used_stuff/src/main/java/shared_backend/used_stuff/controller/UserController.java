package shared_backend.used_stuff.controller;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shared_backend.used_stuff.dto.shop.ShopBoardResponse;
import shared_backend.used_stuff.dto.user.IdResponse;
import shared_backend.used_stuff.dto.user.JoinRequestDto;
import shared_backend.used_stuff.dto.user.JoinResponseDto;
import shared_backend.used_stuff.dto.user.UpdateUserRequest;
import shared_backend.used_stuff.dto.user.UserResponseDto;
import shared_backend.used_stuff.entity.shopboard.ShopBoard;
import shared_backend.used_stuff.entity.user.Password;
import shared_backend.used_stuff.entity.user.Profile;
import shared_backend.used_stuff.entity.user.User;
import shared_backend.used_stuff.exception.NotEqualPassword;
import shared_backend.used_stuff.service.PasswordServiceImpl;
import shared_backend.used_stuff.service.ShopBoardService;
import shared_backend.used_stuff.service.UserService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

	private final PasswordServiceImpl passwordService;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final ShopBoardService shopBoardService;

	@PostMapping("/join")
	public IdResponse join(@RequestBody @Valid JoinRequestDto request) {
		Password password = passwordService.createPassword(request, passwordEncoder);
		Profile profile = userService.createProfile(request);
		User user = userService.createUser(password, profile);

		return new IdResponse(user.getId());
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

	@GetMapping("/user/orderList")
	public List<ShopBoardResponse> orderList(@PageableDefault(size = 10) Pageable pageable,
		@RequestParam(value = "type", required = false) String type,
		@RequestParam(value = "search", required = false) String search){
		// TODO : search, pagination
		// TODO : password Entity에 대한 query문이 두번 발생함.
		return shopBoardService.findOrderListByName(
			SecurityContextHolder.getContext().getAuthentication().getName());

	}


	@PostMapping("/user/edit")
	public IdResponse userEdit(@RequestBody @Valid UpdateUserRequest request){
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Password password = (Password)passwordService.loadUserByUsername(name);
		//해당 부분이 없으면 비밀번호변경이 가능. 따라서 유저 정보 변경과 password 는 분리해야 된다.
		if(!passwordEncoder.matches(request.getPassword(), password.getPassword())){
			throw new NotEqualPassword("비밀번호가 다름");
		}
		User user = password.getUser();
		Profile profile = user.getProfile();

		userService.updateUser(password, profile, request);

		return new IdResponse(user.getId());
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
