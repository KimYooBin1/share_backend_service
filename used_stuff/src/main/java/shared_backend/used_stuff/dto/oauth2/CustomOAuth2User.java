package shared_backend.used_stuff.dto.oauth2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.RequiredArgsConstructor;

/**
 * OAuth2Service 로 data를 전달해주기 위한 DTO
 */
@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {
	private final UserDTO userDto;
	@Override
	public Map<String, Object> getAttributes() {

		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return userDto.getRole();
			}
		});
		return collection;
	}

	@Override
	public String getName() {
		return userDto.getName();
	}

	public String getUsername() {
		return userDto.getUsername();
	}
}
