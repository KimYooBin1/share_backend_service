package shared_backend.used_stuff.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shared_backend.used_stuff.dto.user.JoinRequestDto;
import shared_backend.used_stuff.dto.user.UpdateUserRequest;
import shared_backend.used_stuff.entity.user.Password;
import shared_backend.used_stuff.entity.user.Profile;
import shared_backend.used_stuff.entity.user.User;
import shared_backend.used_stuff.repository.ProfileRepository;
import shared_backend.used_stuff.repository.UserRepository;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final ProfileRepository profileRepository;
	private final PasswordServiceImpl passwordService;
	private final PasswordEncoder passwordEncoder;
	public Profile createProfile(JoinRequestDto request) {
		return new Profile(request.getName(),request.getAge(), request.getGender() ,request.getAddress());
	}

	public Profile findProfile(User user) {
		return profileRepository.findByUser(user);
	}
	@Transactional
	public User createUser(JoinRequestDto request){
		return userRepository.save(
			new User(passwordService.createPassword(request, passwordEncoder), createProfile(request)));
	}
	@Transactional
	public User updateUser(UpdateUserRequest request) {
		User user = passwordService.checkPassword(request).getUser();
		user.getProfile().updateProfile(request);
		return user;
	}

	public User findUser(Long id) {
		return userRepository.findById(id).get();
	}

	public User findByPassword(Password password) {
		return userRepository.findByPassword(password);
	}
}
