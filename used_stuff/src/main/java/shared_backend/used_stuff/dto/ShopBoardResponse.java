package shared_backend.used_stuff.dto;

import lombok.Data;

@Data
public class ShopBoardResponse {
	private Long id;

	public ShopBoardResponse(Long id) {
		this.id = id;
	}
}
