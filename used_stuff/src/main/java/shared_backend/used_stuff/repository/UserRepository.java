package shared_backend.used_stuff.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shared_backend.used_stuff.entity.user.Password;
import shared_backend.used_stuff.entity.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByPassword(Password password);

}
