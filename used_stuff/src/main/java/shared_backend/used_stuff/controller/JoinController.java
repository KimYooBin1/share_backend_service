package shared_backend.used_stuff.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shared_backend.used_stuff.dto.BoardResponse;
import shared_backend.used_stuff.entity.user.Password;
import shared_backend.used_stuff.service.BoardService;
import shared_backend.used_stuff.service.PasswordServiceImpl;

@RestController
@Slf4j
@RequiredArgsConstructor
public class JoinController {

	private final PasswordServiceImpl passwordService;
	private final PasswordEncoder passwordEncoder;
	private final BoardService boardService;

	@PostMapping("/join")
	public String join() {
		Password password = passwordService.createPassword("username", "password", passwordEncoder);
		return "ok";
	}

	@GetMapping("/login")
	public String test(){
		return "ok";
	}
}
