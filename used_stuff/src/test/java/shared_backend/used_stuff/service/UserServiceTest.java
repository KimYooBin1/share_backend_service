package shared_backend.used_stuff.service;


import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import shared_backend.used_stuff.entity.Address;
import shared_backend.used_stuff.entity.user.Gender;
import shared_backend.used_stuff.entity.user.Profile;
import shared_backend.used_stuff.repository.ProfileRepository;

@SpringBootTest
@Transactional
class UserServiceTest {
	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private EntityManager em;

	@Test
	void profile생성(){
		Profile profile = new Profile("name", 10, Gender.male, new Address("","",""));
		Profile save = profileRepository.save(profile);

		em.flush();
		em.clear();

		Optional<Profile> byId = profileRepository.findById(profile.getId());
		LocalDateTime createDate = byId.get().getCreateDate();
		System.out.println("createDate = " + createDate);
	}

}
