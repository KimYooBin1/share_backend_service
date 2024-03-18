package shared_backend.used_stuff.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import shared_backend.used_stuff.dto.user.IdResponse;
import shared_backend.used_stuff.dto.board.CommentResponse;
import shared_backend.used_stuff.dto.board.CreateCommentRequest;
import shared_backend.used_stuff.dto.board.UpdateCommentRequest;
import shared_backend.used_stuff.entity.board.BoardComment;
import shared_backend.used_stuff.service.CommentService;

@RestController
@RequiredArgsConstructor
public class CommentController {
	private final CommentService commentService;

	@GetMapping("/boards/{board_id}/comments")
	public List<CommentResponse> commentList(@PathVariable("board_id") Long boardId) {
		return commentService.comments(boardId);
	}

	@PostMapping("/boards/{board_id}/comments/create")
	public CommentResponse createComment(@PathVariable("board_id") Long boardId,
		@RequestBody @Valid CreateCommentRequest request) {
		return new CommentResponse(boardId, commentService.createComment(boardId, request));
	}

	@PostMapping("/comments/{comment_id}/edit")
	public CommentResponse editComment(@PathVariable("comment_id") Long commentId, @RequestBody UpdateCommentRequest request) {
		BoardComment comment = commentService.editComment(commentId, request);
		return new CommentResponse(comment.getBoard().getId(), comment);
	}

	@PostMapping("/comments/{comment_id}/delete")
	public IdResponse deleteComment(@PathVariable("comment_id") Long commentId, @RequestBody UpdateCommentRequest request) {
		return new IdResponse(commentService.deleteComment(commentId, request).getId());
	}
}
