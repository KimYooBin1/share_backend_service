package shared_backend.used_stuff.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Authentication {
	@Id @GeneratedValue
	private Long id;
}
