package shared_backend.used_stuff.service;

import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shared_backend.used_stuff.dto.user.JoinRequestDto;
import shared_backend.used_stuff.dto.user.UpdatePasswordRequest;
import shared_backend.used_stuff.entity.user.Password;
import shared_backend.used_stuff.entity.user.User;
import shared_backend.used_stuff.exception.AlreadyExistId;
import shared_backend.used_stuff.repository.PasswordRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PasswordServiceImpl implements UserDetailsService {

	private final PasswordRepository passwordRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Password> findOne = passwordRepository.findByUsername(username);

		return findOne.orElseThrow(
			() -> new UsernameNotFoundException(String.format("user_id=%s", username)));
	}

	@Transactional
	public Password createPassword(JoinRequestDto request, PasswordEncoder passwordEncoder){
		if(passwordRepository.existsByUsername(request.getUsername())){
			throw new AlreadyExistId("이미 존제하는 user name 입니다");
		}
		return new Password(request.getUsername(), passwordEncoder.encode(request.getPassword()), "user");
	}

	@Transactional
	public Password updatePassword(Password password, UpdatePasswordRequest request, PasswordEncoder encoder) {
		password.updatePassword(request, encoder);
		return password;
	}

	public User findUser() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("name = {}", name);
		Password password = (Password)loadUserByUsername(name);
		return password.getUser();
	}
}
