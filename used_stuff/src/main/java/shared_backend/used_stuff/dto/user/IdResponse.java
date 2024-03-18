package shared_backend.used_stuff.dto.user;

import lombok.Data;

@Data
public class IdResponse {
	private Long id;

	public IdResponse(Long id) {
		this.id = id;
	}
}
