package shared_backend.used_stuff.dto.board;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.Data;

@Data
public class BoardListDto {
	private Long Id;
	private String writer;
	private String title;
	private LocalDateTime createDate;
	private Integer likes;

	@QueryProjection
	public BoardListDto(Long id, String writer, String title, LocalDateTime createDate, Integer likes) {
		Id = id;
		this.writer = writer;
		this.title = title;
		this.createDate = createDate;
		this.likes = likes;
	}
}
