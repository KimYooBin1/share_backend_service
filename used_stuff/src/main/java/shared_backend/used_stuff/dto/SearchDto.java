package shared_backend.used_stuff.dto;

import static lombok.AccessLevel.*;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class SearchDto {
	private String type;
	private String search;
}
