package shared_backend.used_stuff.controller;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shared_backend.used_stuff.dto.SearchDto;
import shared_backend.used_stuff.dto.shop.ShopBoardResponse;
import shared_backend.used_stuff.dto.user.IdResponse;
import shared_backend.used_stuff.dto.user.JoinRequestDto;
import shared_backend.used_stuff.dto.user.UpdatePasswordRequest;
import shared_backend.used_stuff.dto.user.UpdateUserRequest;
import shared_backend.used_stuff.dto.user.UserResponseDto;
import shared_backend.used_stuff.entity.user.Password;
import shared_backend.used_stuff.entity.user.User;
import shared_backend.used_stuff.service.PasswordServiceImpl;
import shared_backend.used_stuff.service.ShopBoardService;
import shared_backend.used_stuff.service.UserService;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
	private final PasswordServiceImpl passwordService;
	private final UserService userService;
	private final ShopBoardService shopBoardService;

	@PostMapping("/join")
	public IdResponse join(@RequestBody @Valid JoinRequestDto request) {
		return new IdResponse(userService.createUser(request).getId());
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
		Password password = (Password)passwordService.loadUserByUsername(
			SecurityContextHolder.getContext().getAuthentication().getName());
		return new UserResponseDto(password, password.getUser().getProfile(), password.getUser().getPoint());
	}

	@GetMapping("/user/orderList")
	public Page<ShopBoardResponse> orderList(@PageableDefault(size = 10) Pageable pageable, SearchDto search){
		// TODO : search sort
		System.out.println("search = " + search.getType());
		return shopBoardService.findOrderListByName(
			SecurityContextHolder.getContext().getAuthentication().getName(), search, pageable);
	}

	@PostMapping("/user/edit")
	public IdResponse userEdit(@RequestBody @Valid UpdateUserRequest request){
		return new IdResponse(userService.updateUser(request).getId());
	}

	@PostMapping("/user/edit/password")
	public IdResponse userEditPassword(@RequestBody @Valid UpdatePasswordRequest request) {
		return new IdResponse(
			passwordService.updatePassword(request)
				.getUser()
				.getId());
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
