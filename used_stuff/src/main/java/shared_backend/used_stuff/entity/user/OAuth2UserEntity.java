package shared_backend.used_stuff.entity.user;

import static lombok.AccessLevel.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
// TODO : setter 지우기
@Setter
@NoArgsConstructor(access = PROTECTED)
public class OAuth2UserEntity {
	@Id
	@GeneratedValue
	private Long id;

	private String username;
	private String name;
	private String email;
	private String role;

	public OAuth2UserEntity(String username, String name, String email, String role) {
		this.username = username;
		this.name = name;
		this.email = email;
		this.role = role;
	}
}
