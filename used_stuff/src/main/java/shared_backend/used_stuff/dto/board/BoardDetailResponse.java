package shared_backend.used_stuff.dto.board;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.ToString;
import shared_backend.used_stuff.entity.board.Board;

@Data
@ToString(of = {"id", "writer"})

public class BoardDetailResponse {
	private Long id;
	private String writer;
	private String password;
	private String title;
	private String content;
	private int like;
	private int dislike;
	private LocalDateTime createDate;

	public BoardDetailResponse(Board board) {
		this.id = board.getId();
		this.writer = board.getWriter();
		this.password = board.getPassword();
		this.title = board.getTitle();
		this.content = board.getContent();
		this.like = board.getLikes();
		this.dislike = board.getDislikes();
		this.createDate = board.getCreateDate();
	}
}
