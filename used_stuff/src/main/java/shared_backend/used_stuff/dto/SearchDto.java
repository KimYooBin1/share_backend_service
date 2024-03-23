package shared_backend.used_stuff.dto;

import static lombok.AccessLevel.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = PROTECTED)
public class SearchDto {
	private String type;
	private String search;
}
