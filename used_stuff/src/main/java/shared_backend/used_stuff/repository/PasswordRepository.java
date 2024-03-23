package shared_backend.used_stuff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import shared_backend.used_stuff.entity.user.Password;
import shared_backend.used_stuff.repository.custom.PasswordRepositoryCustom;

public interface PasswordRepository extends JpaRepository<Password, String>, PasswordRepositoryCustom {
	@EntityGraph("password-with-user-and-profile")
	Optional<Password> findByUsername(String username);

	boolean existsByUsername(String username);
}
