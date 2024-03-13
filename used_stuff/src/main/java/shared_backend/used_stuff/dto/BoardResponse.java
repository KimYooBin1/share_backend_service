package shared_backend.used_stuff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import shared_backend.used_stuff.entity.board.Board;

@Data
@ToString(of = {"id", "writer"})

public class BoardResponse {
	private Long id;
	private String writer;
	private String password;
	private String title;
	private String content;
	private int like;
	private int dislike;

	public BoardResponse(Board board) {
		this.id = board.getId();
		this.writer = board.getWriter();
		this.password = board.getPassword();
		this.title = board.getTitle();
		this.content = board.getContent();
		this.like = board.getLikes();
		this.dislike = board.getDislikes();
	}
}
