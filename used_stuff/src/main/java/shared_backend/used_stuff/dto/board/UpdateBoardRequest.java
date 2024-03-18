package shared_backend.used_stuff.dto.board;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateBoardRequest {
	@NotEmpty
	private String password;
	private String title;
	private String content;

	public UpdateBoardRequest(String password, String title, String content) {
		this.password = password;
		this.title = title;
		this.content = content;
	}

	public UpdateBoardRequest(String password) {
		this.password = password;
	}
}
