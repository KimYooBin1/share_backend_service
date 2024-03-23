package shared_backend.used_stuff.repository.custom;

import java.util.Optional;

import shared_backend.used_stuff.entity.user.User;

public interface PasswordRepositoryCustom {
	Optional<User> findByUsernameFetchProfile(String name);
}
