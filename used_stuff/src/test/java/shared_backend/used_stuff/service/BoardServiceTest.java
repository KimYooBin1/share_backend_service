package shared_backend.used_stuff.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import shared_backend.used_stuff.dto.CreateBoardRequest;
import shared_backend.used_stuff.dto.UpdateBoardRequest;
import shared_backend.used_stuff.entity.board.Board;
import shared_backend.used_stuff.entity.board.Status;
import shared_backend.used_stuff.exception.AlreadyDeletedException;
import shared_backend.used_stuff.exception.NotEqualPassword;

@SpringBootTest
@Transactional
class BoardServiceTest {
	@Autowired BoardService boardService;
	@Autowired EntityManager em;

	@Test
	@DisplayName("게시글 생성")
	void 게시글_생성(){
		Board board = createBoard();
		em.flush();
		em.clear();

		Board findBoard = boardService.findBoard(board.getId());

		assertThat(findBoard.getId()).isEqualTo(board.getId());
		assertThat(findBoard.getPassword()).isEqualTo(board.getPassword());
		assertThat(findBoard.getTitle()).isEqualTo(board.getTitle());
		assertThat(findBoard.getWriter()).isEqualTo(board.getWriter());
		assertThat(findBoard.getContent()).isEqualTo(board.getContent());
	}


	@Test
	@DisplayName("게시글 수정")
	void 게시글_수정(){
		Board board = createBoard();
		Board editBoard = updateBoard(board,"password");
		em.flush();
		em.clear();
		assertEquals(editBoard.getContent(), "contents");
		assertThat(editBoard.getStatus()).isEqualTo(Status.edit);
	}

	@Test
	@DisplayName("게시글 수정 비밀번호 에러")
	void 게시글_수정_실패(){
		Board board = createBoard();
		Assertions.assertThrows(NotEqualPassword.class, () -> {
			updateBoard(board, "password1");
		});
	}

	@Test
	@DisplayName("게시글 삭제")
	void 게시글_삭제() {
		Board board = createBoard();
		Board deleteBoard = deleteBoard(board, "password");
		assertThat(deleteBoard.getStatus()).isEqualTo(Status.delete);
	}

	@Test
	@DisplayName("게시글 삭제 비밀번호 에러")
	void 게시글_삭제_비밀번호_에러(){
		Board board = createBoard();
		Assertions.assertThrows(NotEqualPassword.class, () -> {
			deleteBoard(board, "password1");
		});
	}

	@Test
	@DisplayName("삭제된 게시글 수정 에러")
	void 삭제된_게시글_수정() {
		Board board = createBoard();
		Board deleteBoard = deleteBoard(board, "password");
		Assertions.assertThrows(AlreadyDeletedException.class, () -> {
			updateBoard(deleteBoard, "password");
		});
	}

	@Test
	@DisplayName("삭제된 게시글 재삭제")
	void 삭제된_게시글_재삭제(){
		Board board = createBoard();
		Board deleteBoard = deleteBoard(board, "password");
		Assertions.assertThrows(AlreadyDeletedException.class, () -> {
			deleteBoard(deleteBoard, "password");
		});
	}

	private Board createBoard() {
		CreateBoardRequest request = new CreateBoardRequest("writer", "password", "title", "content");
		return boardService.createBoard(request);
	}

	private Board updateBoard(Board board, String password){
		Board findBoard = boardService.findBoard(board.getId());
		UpdateBoardRequest request = new UpdateBoardRequest(password, "title", "contents");
		return boardService.editBoard(findBoard.getId(), request);
	}
	private Board deleteBoard(Board board, String password){
		Board findBoard = boardService.findBoard(board.getId());
		UpdateBoardRequest request = new UpdateBoardRequest(password);
		return boardService.deleteBoard(findBoard.getId(), request);
	}



}
