package shared_backend.used_stuff.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Password {
	@Id @GeneratedValue
	private Long id;
}
