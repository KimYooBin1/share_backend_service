package shared_backend.used_stuff.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import shared_backend.used_stuff.entity.user.OAuth2UserEntity;

public interface OAuth2UserRepository extends JpaRepository<OAuth2UserEntity, Long> {
	OAuth2UserEntity findByUsername(String username);
}
