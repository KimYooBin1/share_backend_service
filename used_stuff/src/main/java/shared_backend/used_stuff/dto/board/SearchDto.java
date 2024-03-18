package shared_backend.used_stuff.dto.board;

import static lombok.AccessLevel.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class SearchDto {
	private String type;
	private String search;

	public SearchDto(String type, String search) {
		this.type = type;
		this.search = search;
	}
}
