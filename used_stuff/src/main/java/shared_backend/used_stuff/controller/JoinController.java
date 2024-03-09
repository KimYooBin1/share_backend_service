package shared_backend.used_stuff.controller;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shared_backend.used_stuff.entity.user.Password;
import shared_backend.used_stuff.service.PasswordServiceImpl;

@RestController
@RequiredArgsConstructor
public class JoinController {

	private final PasswordServiceImpl passwordService;
	private final PasswordEncoder passwordEncoder;
	@PostMapping("/join")
	public String join(){
		Password password = passwordService.createPassword("username", "password", passwordEncoder);
		return "ok";
	}
}
