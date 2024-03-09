package shared_backend.used_stuff.entity.user;

import static jakarta.persistence.EnumType.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id @GeneratedValue
	private Long id;

	@Enumerated(STRING)
	private LoginType type;

	// @OneToOne(mappedBy = "user")
	// private Password password;
}
