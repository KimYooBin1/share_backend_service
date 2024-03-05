package shared_backend.used_stuff.controller;

import static java.util.stream.Collectors.*;
import static shared_backend.used_stuff.entity.board.Status.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import shared_backend.used_stuff.dto.CommentResponse;
import shared_backend.used_stuff.dto.CreateCommentRequest;
import shared_backend.used_stuff.dto.UpdateCommentRequest;
import shared_backend.used_stuff.entity.board.Board;
import shared_backend.used_stuff.entity.board.BoardComment;
import shared_backend.used_stuff.entity.board.Status;
import shared_backend.used_stuff.repository.CommentRepository;
import shared_backend.used_stuff.service.BoardService;

@RestController
@RequiredArgsConstructor
@Transactional
public class CommentController {
	private final CommentRepository commentRepository;
	private final BoardService boardService;

	@GetMapping("/boards/{board_id}/comments")
	public List<CommentResponse> commentList(@PathVariable("board_id") Long boardId) {
		Board board = boardService.findBoard(boardId);
		List<BoardComment> comments = commentRepository.findAllByBoard(board);
		return comments.stream().map(comment -> {
			return new CommentResponse(boardId, comment);
		}).collect(toList());
	}

	@PostMapping("/boards/{board_id}/comments/create")
	public CommentResponse createComment(@PathVariable("board_id") Long boardId,
		@RequestBody @Valid CreateCommentRequest request) {
		Board board = boardService.findBoard(boardId);
		BoardComment comment = new BoardComment(request.getWriter(), request.getPassword(), request.getContent());
		board.addComment(comment);
		commentRepository.save(comment);
		return new CommentResponse(boardId, comment);
	}

	@PostMapping("/comments/{comment_id}/edit")
	public CommentResponse editComment(@PathVariable("comment_id") Long commentId, @RequestBody UpdateCommentRequest request) {
		BoardComment comment = commentRepository.findById(commentId).get();
		boardService.checkPW(comment.getPassword(), request.getPassword());
		comment.setContent(request.getContent());

		return new CommentResponse(comment.getBoard().getId(), comment);
	}


	@PostMapping("/comments/{comment_id}/delete")
	public CommentResponse deleteComment(@PathVariable("comment_id") Long commentId, @RequestBody UpdateCommentRequest request) {
		BoardComment comment = commentRepository.findById(commentId).get();
		comment.setStatus(delete);

		return new CommentResponse(comment.getBoard().getId(), comment);
	}

}
