package shared_backend.used_stuff.entity.board;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter @Getter
public class Board {
	@Id @GeneratedValue
	private Long id;

	@OneToMany(mappedBy = "board", cascade = ALL, orphanRemoval = true)
	private List<BoardComment> boardComments = new ArrayList<>();

	private String writer;

	private String password;

	private String content;

	@Enumerated(STRING)
	private BoardStatus status;

	private Long likes;

	private Long dislikes;

	private LocalDateTime createDate;

	private LocalDateTime updateDate;

	//연관관계 메소드
	public void addComment(BoardComment comment) {
		comment.setBoard(this);
		this.getBoardComments().add(comment);
	}
}
