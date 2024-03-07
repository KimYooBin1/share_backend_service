package shared_backend.used_stuff.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static shared_backend.used_stuff.entity.board.Status.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import shared_backend.used_stuff.dto.CreateBoardRequest;
import shared_backend.used_stuff.dto.CreateCommentRequest;
import shared_backend.used_stuff.dto.UpdateBoardRequest;
import shared_backend.used_stuff.dto.UpdateCommentRequest;
import shared_backend.used_stuff.entity.board.Board;
import shared_backend.used_stuff.entity.board.BoardComment;
import shared_backend.used_stuff.exception.AlreadyDeletedException;
import shared_backend.used_stuff.exception.NotEqualPassword;

@SpringBootTest
@Transactional
class CommentServiceTest {
	@Autowired CommentService commentService;
	@Autowired BoardService boardService;

	@Test
	@DisplayName("댓글 생성")
	void 댓글_생성(){
		Board board = createBoard();
		BoardComment comment = createComment(board);

		assertThat(comment.getBoard().getId()).isEqualTo(board.getId());
	}


	@Test
	@DisplayName("댓글 수정")
	void 댓글_수정() {
		Board board = createBoard();

		BoardComment editComment = updateComment(board, "password");
		assertThat(editComment.getStatus()).isEqualTo(edit);
	}

	@Test
	@DisplayName("댓글 수정 비밀번호 에러")
	void 댓글_수정_비밀번호_에러(){
		Board board = createBoard();
		Assertions.assertThrows(NotEqualPassword.class, () -> {
			updateComment(board, "password1");
		});
	}

	@Test
	@DisplayName("댓글 삭제")
	void 댓글_삭제(){
		Board board = createBoard();
		Board deleteBoard = deleteBoard(board, "password");

		assertThat(deleteBoard.getStatus()).isEqualTo(delete);
	}

	@Test
	@DisplayName("댓글 삭제 비밀번호 에러")
	void 댓글_삭제_비밀번호_에러() {
		Board board = createBoard();
		assertThrows(NotEqualPassword.class, () -> {
			deleteBoard(board, "password1");
		});
	}

	@Test
	@DisplayName("삭제된 게시글의 댓글 수정")
	void 삭제된_게시글_댓글수정() {
		Board board = createBoard();
		Board deleteBoard = deleteBoard(board, "password");
		assertThrows(AlreadyDeletedException.class, () -> {
			updateComment(deleteBoard, "password");
		});
	}

	@Test
	@DisplayName("삭제된 게시글의 댓글 삭제")
	void 삭제된_게시글_댓글삭제() {
		Board board = createBoard();
		Board deleteBoard = deleteBoard(board, "password");
		assertThrows(AlreadyDeletedException.class, () -> {
			deleteBoard(deleteBoard, "password");
		});
	}

	private Board createBoard() {
		CreateBoardRequest request = new CreateBoardRequest("writer", "password", "title", "content");
		return boardService.createBoard(request);
	}

	private Board deleteBoard(Board board, String password){
		Board findBoard = boardService.findBoard(board.getId());
		UpdateBoardRequest request = new UpdateBoardRequest(password);
		return boardService.deleteBoard(findBoard.getId(), request);
	}

	private BoardComment createComment(Board board) {
		CreateCommentRequest request = new CreateCommentRequest("writer", "password", "content");
		return commentService.createComment(board.getId(), request);
	}

	private BoardComment updateComment(Board board, String password) {
		BoardComment comment = createComment(board);
		UpdateCommentRequest request = new UpdateCommentRequest(password, "contents");
		BoardComment findComment = commentService.findComment(comment.getId());
		BoardComment editComment = commentService.editComment(findComment.getId(), request);
		return editComment;
	}

	private BoardComment deleteComment(Board board, String password) {
		BoardComment comment = createComment(board);
		UpdateCommentRequest request = new UpdateCommentRequest(password);
		BoardComment findComment = commentService.findComment(comment.getId());
		BoardComment deleteComment = commentService.deleteComment(findComment.getId(), request);
		return deleteComment;
	}
}
