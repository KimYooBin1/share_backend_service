package shared_backend.used_stuff.service;

import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shared_backend.used_stuff.dto.JoinRequestDto;
import shared_backend.used_stuff.entity.Address;
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

	public Profile createProfile(JoinRequestDto request) {
		Profile profile = new Profile(request.getName(),request.getAge(), request.getGender() ,request.getAddress());
		profileRepository.save(profile);

		return profile;
	}

	public User createUser(Password password, Profile profile){
		User user = new User(password, profile);
		userRepository.save(user);

		return user;
	}
}
