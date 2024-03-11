package shared_backend.used_stuff.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shared_backend.used_stuff.entity.user.Profile;
import shared_backend.used_stuff.entity.user.User;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
	Profile findByUser(User user);
}
