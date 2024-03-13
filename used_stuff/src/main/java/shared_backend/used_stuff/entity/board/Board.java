package shared_backend.used_stuff.entity.board;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.EnumType.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import shared_backend.used_stuff.base.BaseEntity;

@Entity
@Setter @Getter
public class Board extends BaseEntity {
	@Id @GeneratedValue
	private Long id;

	@OneToMany(mappedBy = "board", cascade = ALL, orphanRemoval = true)
	private List<BoardComment> boardComments = new ArrayList<>();

	private String writer;

	private String password;

	private String title;

	private String content;

	@Enumerated(STRING)
	private Status status;

	private int likes;

	private int dislikes;


	//연관관계 메소드
	public void addComment(BoardComment comment) {
		comment.setBoard(this);
		this.getBoardComments().add(comment);
	}

	public Board() {
	}

	public void likeBoard() {
		this.likes = likes + 1;
	}

	public void dislikeBoard() {
		this.dislikes = dislikes + 1;
	}

	public Board(String writer, String password, String title, String content) {
		this.writer = writer;
		this.password = password;
		this.title = title;
		this.content = content;
		this.status = Status.regist;
		this.likes = 0;
		this.dislikes = 0;
	}

}
