package shared_backend.used_stuff.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shared_backend.used_stuff.entity.user.Password;
import shared_backend.used_stuff.repository.PasswordRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PasswordServiceImpl implements UserDetailsService {

	private final PasswordRepository passwordRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Password> findOne = passwordRepository.findByUsername(username);
		log.info("username = {}",findOne.get().getUsername());
		return findOne.orElseThrow(
			() -> new UsernameNotFoundException(String.format("user_id=%s", username)));
	}

	public Password createPassword(String username, String pw, PasswordEncoder passwordEncoder){
		Password password = new Password();
		password.setUsername(username);
		password.setPasswordHashed(passwordEncoder.encode(pw));
		password.setRole("user");
		return password;
	}
}
