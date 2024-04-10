package shared_backend.used_stuff.controller;

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
	public ResponseEntity<Page<CommentResponse>> commentList(@PathVariable("board_id") Long boardId,
		@PageableDefault(size = 10) Pageable pageable) {
		return ResponseEntity.ok(commentService.comments(boardId, pageable));
	}

	@PostMapping("/boards/{board_id}/comments/create")
	public ResponseEntity<CommentResponse> createComment(@PathVariable("board_id") Long boardId,
		@RequestBody @Valid CreateCommentRequest request) {
		return ResponseEntity.ok(new CommentResponse(commentService.createComment(boardId, request)));
	}

	@PostMapping("/comments/{comment_id}/edit")
	public ResponseEntity<CommentResponse> editComment(@PathVariable("comment_id") Long commentId, @RequestBody UpdateCommentRequest request) {
		BoardComment comment = commentService.editComment(commentId, request);
		return ResponseEntity.ok(new CommentResponse(comment));
	}

	@PostMapping("/comments/{comment_id}/delete")
	public ResponseEntity<IdResponse> deleteComment(@PathVariable("comment_id") Long commentId, @RequestBody UpdateCommentRequest request) {
		return ResponseEntity.ok(new IdResponse(commentService.deleteComment(commentId, request).getId()));
	}
}
