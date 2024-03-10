package shared_backend.used_stuff.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shared_backend.used_stuff.entity.user.Password;
import shared_backend.used_stuff.entity.user.User;
import shared_backend.used_stuff.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordServiceImpl passwordService;

	public User createUser(Password password){
		User user = new User(password);
		userRepository.save(user);

		return user;
	}
}
