package shared_backend.used_stuff.controller;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shared_backend.used_stuff.dto.board.BoardResponse;
import shared_backend.used_stuff.dto.board.CreateBoardRequest;
import shared_backend.used_stuff.dto.board.BoardDetailResponse;
import shared_backend.used_stuff.dto.user.IdResponse;
import shared_backend.used_stuff.dto.SearchDto;
import shared_backend.used_stuff.dto.board.UpdateBoardRequest;
import shared_backend.used_stuff.dto.board.BoardListDto;
import shared_backend.used_stuff.service.BoardService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardController {
	private final BoardService boardService;

	@GetMapping("/boards")
	public ResponseEntity<Page<BoardListDto>> boards(@PageableDefault(size=10) Pageable pageable,
		SearchDto search){
		return ResponseEntity.ok(boardService.boardList(search, pageable));
	}

	@GetMapping("/boards/best")
	public ResponseEntity<List<BoardListDto>> bestBoard() {
		return ResponseEntity.ok(boardService.bestBoards());
	}

	@GetMapping("/boards/{id}/detail")
	public ResponseEntity<BoardDetailResponse> detailBoard(@PathVariable("id") Long id) {
		return ResponseEntity.ok(new BoardDetailResponse(boardService.findBoard(id)));
	}

	@PostMapping("/boards/create")
	public ResponseEntity<BoardResponse> createBoard(@RequestBody @Valid CreateBoardRequest request) {
		return ResponseEntity.ok(new BoardResponse(boardService.createBoard(request)));
	}

	@PostMapping("/boards/{id}/edit")
	public ResponseEntity<BoardResponse> editBoard(@PathVariable("id") Long id, @RequestBody @Valid UpdateBoardRequest request) {
		return ResponseEntity.ok(new BoardResponse(boardService.editBoard(id, request)));
	}

	@PostMapping("/boards/{id}/delete")
	public ResponseEntity<IdResponse> deleteBoard(@PathVariable("id") Long id, @RequestBody @Valid UpdateBoardRequest request) {
		return ResponseEntity.ok(new IdResponse(boardService.deleteBoard(id, request).getId()));
	}

	@PostMapping("/boards/{id}/like")
	public ResponseEntity<BoardResponse> likeBoard(@PathVariable("id") Long id) {
		return ResponseEntity.ok(new BoardResponse(boardService.likeBoard(id, "like")));
	}

	@PostMapping("/boards/{id}/dislike")
	public ResponseEntity<BoardResponse> dislikeBoard(@PathVariable("id") Long id) {
		return ResponseEntity.ok(new BoardResponse(boardService.likeBoard(id, "dislike")));
	}

}
