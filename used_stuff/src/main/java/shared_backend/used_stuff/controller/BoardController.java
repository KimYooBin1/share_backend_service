package shared_backend.used_stuff.controller;


import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import shared_backend.used_stuff.dto.CreateBoardRequest;
import shared_backend.used_stuff.dto.BoardResponse;
import shared_backend.used_stuff.entity.board.Board;
import shared_backend.used_stuff.entity.board.BoardStatus;
import shared_backend.used_stuff.repository.BoardRepository;

@RestController
@RequiredArgsConstructor
@Transactional
public class BoardController {

	private final BoardRepository boardRepository;

	@GetMapping("/boards")
	public List<BoardResponse> boardList(@RequestParam(value = "page", defaultValue = "0", required = false) int page) {
		PageRequest pageRequest = PageRequest.of(page, 10);

		return boardRepository.findAll(pageRequest).stream().map(BoardResponse::new).collect(toList());
	}

	@GetMapping("/boards/best")
	public List<BoardResponse> bestBoard() {
		PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("likes").descending());

		return boardRepository.findAll(pageRequest).stream().map(BoardResponse::new).collect(toList());
	}

	@GetMapping("/boards/{id}")
	public BoardResponse detailBoard(@PathVariable("id") Long id) {
		Board board = boardRepository.findById(id).get();
		return new BoardResponse(board);
	}

	@PostMapping("/boards/create")
	public BoardResponse createBoard(@RequestBody @Valid CreateBoardRequest request) {
		Board board = new Board(request.getWriter(), request.getPassword(), request.getTitle(), request.getContent());
		boardRepository.save(board);

		return new BoardResponse(board);
	}

	@PostMapping("/boards/{id}/edit")
	public void editBoard(@PathVariable("id") Long id) {
	}

	@PostMapping("/boards/{id}/delete")
	public void deleteBoard(@PathVariable("id") Long id) {
		Board board = boardRepository.findById(id).get();
		board.setStatus(BoardStatus.delete);
	}

	@PostMapping("/boards/{id}/like")
	public BoardResponse likeBoard(@PathVariable("id") Long id) {
		Board board = boardRepository.findById(id).get();
		board.likeBoard();

		return new BoardResponse(board);
	}

	@PostMapping("/boards/{id}/dislike")
	public BoardResponse dislikeBoard(@PathVariable("id") Long id) {
		Board board = boardRepository.findById(id).get();
		board.dislikeBoard();

		return new BoardResponse(board);
	}
}
