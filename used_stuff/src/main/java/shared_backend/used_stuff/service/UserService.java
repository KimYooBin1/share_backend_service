package shared_backend.used_stuff.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shared_backend.used_stuff.dto.JoinRequestDto;
import shared_backend.used_stuff.dto.UpdateUserRequest;
import shared_backend.used_stuff.entity.user.Password;
import shared_backend.used_stuff.entity.user.Profile;
import shared_backend.used_stuff.entity.user.User;
import shared_backend.used_stuff.repository.ProfileRepository;
import shared_backend.used_stuff.repository.UserRepository;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final ProfileRepository profileRepository;
	private final PasswordServiceImpl passwordService;
	private final PasswordEncoder encoder;
	public Profile createProfile(JoinRequestDto request) {
		return new Profile(request.getName(),request.getAge(), request.getGender() ,request.getAddress());
	}

	public Profile findProfile(User user) {
		return profileRepository.findByUser(user);
	}

	public User createUser(Password password, Profile profile){
		User user = new User(password, profile);
		userRepository.save(user);

		return user;
	}

	public void updateUser(Password password, Profile profile, UpdateUserRequest request) {
		profile.updateProfile(request);
		password.updatePassword(request, encoder);
	}

	public User findUser(Long id) {
		return userRepository.findById(id).get();
	}

	public User findByPassword(Password password) {
		return userRepository.findByPassword(password);
	}
}
