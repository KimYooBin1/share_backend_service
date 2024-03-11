package shared_backend.used_stuff.entity.user;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shared_backend.used_stuff.dto.UpdateUserRequest;
import shared_backend.used_stuff.entity.shopboard.ShopBoard;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
public class User {
	@Id @GeneratedValue
	private Long id;

	@Enumerated(STRING)
	private LoginType type;

	private int point;

	@OneToOne(mappedBy = "user", cascade = ALL, orphanRemoval = true)
	private Password password;

	@OneToOne(mappedBy = "user", cascade = ALL, orphanRemoval = true)
	private Profile profile;

	@OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true)
	private List<ShopBoard> boards = new ArrayList<>();

	public User(Password password, Profile profile) {
		this.type = LoginType.login;
		this.password = password;
		this.profile = profile;
		this.point = 0;
		profile.setUser(this);
		password.setUser(this);
	}
}
