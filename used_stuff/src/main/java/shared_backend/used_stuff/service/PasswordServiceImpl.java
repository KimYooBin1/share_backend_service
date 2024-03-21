package shared_backend.used_stuff.service;

import java.util.Objects;
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
import shared_backend.used_stuff.dto.user.UpdateUserRequest;
import shared_backend.used_stuff.entity.user.Password;
import shared_backend.used_stuff.entity.user.User;
import shared_backend.used_stuff.exception.AlreadyExistId;
import shared_backend.used_stuff.exception.NotEqualPassword;
import shared_backend.used_stuff.repository.PasswordRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PasswordServiceImpl implements UserDetailsService {

	private final PasswordRepository passwordRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//TODO : 여기서 중복 쿼리가 2번 나가는거 같은데 querydsl로 바꿔서도 fetch join 해보기
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
	public Password updatePassword(UpdatePasswordRequest request) {
		Password password = checkPassword(request);
		passwordDuplicateCheck(request.getCheckPassword(), request.getEditPassword());
		password.updatePassword(request, passwordEncoder);
		return password;
	}

	public User findUser() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("name = {}", name);
		Password password = (Password)loadUserByUsername(name);
		return password.getUser();
	}

	public Password checkPassword(UpdatePasswordRequest request){
		Password password = (Password)loadUserByUsername(
			SecurityContextHolder.getContext().getAuthentication().getName());
		if(!passwordEncoder.matches(request.getCheckPassword(), password.getPassword())){
			throw new NotEqualPassword("비밀번호가 다름");
		}
		return password;
	}

	public Password checkPassword(UpdateUserRequest request){
		Password password = (Password)loadUserByUsername(
			SecurityContextHolder.getContext().getAuthentication().getName());
		if(!passwordEncoder.matches(request.getPassword(), password.getPassword())){
			throw new NotEqualPassword("비밀번호가 다름");
		}
		return password;
	}

	public void passwordDuplicateCheck(String postPassword, String editPassword) {
		if(Objects.equals(postPassword, editPassword)){
			throw new RuntimeException("비밀번호는 동일하면 안됩니다");
		}
	}
}
