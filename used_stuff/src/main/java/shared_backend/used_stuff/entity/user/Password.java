package shared_backend.used_stuff.entity.user;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Password implements UserDetails {

	@Id @GeneratedValue
	private Long id;

	private String username; //사용자 아이디

	private String passwordHashed;	//암호화된 비밀번호

	private String role;

	public Password(String username, String passwordHashed, String role) {
		this.username = username;
		this.passwordHashed = passwordHashed;
		this.role = role;
	}

	// @OneToOne
	// @JoinColumn(name = "user_id")
	// private User user;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();	//사용자별 권한 설정. admin, user? 지금은 사용하지 않음
		collection.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return role;
			}
		});
		return collection;
	}

	@Override
	public String getPassword() {
		return passwordHashed;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;	//유효한 계쩡
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;	//사용불가하지 않은 계정
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;	//비밀번호가 만료되지 않음
	}

	@Override
	public boolean isEnabled() {
		return true;	//이용가능한 계쩡?
	}
}
