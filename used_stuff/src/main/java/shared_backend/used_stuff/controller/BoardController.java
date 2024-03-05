package shared_backend.used_stuff.controller;


import java.io.IOException;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import shared_backend.used_stuff.dto.CreateBoardRequest;
import shared_backend.used_stuff.dto.BoardResponse;
import shared_backend.used_stuff.dto.UpdateBoardRequest;
import shared_backend.used_stuff.service.BoardService;

@RestController
@RequiredArgsConstructor
@Transactional
public class BoardController {

	private final BoardService boardService;
	/**
	 * 현재 페이지와 검색어를 통해 Board를 10개씩 출력
	 * @param page : 현재 페이지 출력
	 * @return : RequestDto 출력
	 */
	@GetMapping("/boards")
	public List<BoardResponse> boardList(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
		HttpServletResponse response) throws IOException {
		return boardService.boards(page, response);
	}

	@GetMapping("/boards/best")
	public List<BoardResponse> bestBoard() {
		return boardService.bestBoards();
	}

	@GetMapping("/boards/{id}")
	public BoardResponse detailBoard(@PathVariable("id") Long id) {
		return new BoardResponse(boardService.findBoard(id));
	}

	@PostMapping("/boards/create")
	public BoardResponse createBoard(@RequestBody @Valid CreateBoardRequest request) {
		return new BoardResponse(boardService.createBoard(request));
	}

	@PostMapping("/boards/{id}/edit")
	public BoardResponse editBoard(@PathVariable("id") Long id, @RequestBody @Valid UpdateBoardRequest request) {
		return new BoardResponse(boardService.editBoard(id, request));
	}

	@PostMapping("/boards/{id}/delete")
	public void deleteBoard(@PathVariable("id") Long id, @RequestBody @Valid UpdateBoardRequest request) {
		boardService.deleteBoard(id, request);
	}

	@PostMapping("/boards/{id}/like")
	public BoardResponse likeBoard(@PathVariable("id") Long id) {
		return new BoardResponse(boardService.likeBoard(id, "like"));
	}

	@PostMapping("/boards/{id}/dislike")
	public BoardResponse dislikeBoard(@PathVariable("id") Long id) {

		return new BoardResponse(boardService.likeBoard(id, "dislike"));
	}
}
