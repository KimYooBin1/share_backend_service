package shared_backend.used_stuff.dto;

import lombok.Data;

@Data
public class IdResponse {
	private Long id;

	public IdResponse(Long id) {
		this.id = id;
	}
}
