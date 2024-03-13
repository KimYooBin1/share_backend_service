package shared_backend.used_stuff.service;

import static java.time.LocalDateTime.*;
import static java.util.stream.Collectors.*;
import static shared_backend.used_stuff.entity.board.Status.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shared_backend.used_stuff.dto.CommentResponse;
import shared_backend.used_stuff.dto.CreateCommentRequest;
import shared_backend.used_stuff.dto.UpdateCommentRequest;
import shared_backend.used_stuff.entity.board.Board;
import shared_backend.used_stuff.entity.board.BoardComment;
import shared_backend.used_stuff.repository.CommentRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService extends Check {
	private final CommentRepository commentRepository;
	private final BoardService boardService;
	public List<CommentResponse> comments(Long boardId) {
		Board board = boardService.findBoard(boardId);
		List<BoardComment> comments = commentRepository.findAllByBoard(board);
		return comments.stream().map(comment -> {
			return new CommentResponse(boardId, comment);
		}).collect(toList());
	}

	public BoardComment createComment(Long boardId, CreateCommentRequest request) {
		Board board = boardService.findBoard(boardId);
		checkState(board.getStatus());
		BoardComment comment = new BoardComment(request.getWriter(), request.getPassword(), request.getContent());
		board.addComment(comment);
		commentRepository.save(comment);

		return comment;
	}

	public BoardComment editComment(Long commentId, UpdateCommentRequest request) {
		BoardComment comment = commentRepository.findById(commentId).get();
		//comment board fetch join 으로 변경하기
		checkState(comment.getBoard().getStatus());
		checkState(comment.getStatus());
		checkPW(comment.getPassword(), request.getPassword());
		comment.setContent(request.getContent());
		comment.setStatus(edit);

		return comment;
	}

	public BoardComment deleteComment(Long commentId, UpdateCommentRequest request){
		BoardComment comment = commentRepository.findById(commentId).get();
		//comment board fetch join 으로 변경하기
		checkState(comment.getBoard().getStatus());
		checkState(comment.getStatus());
		checkPW(comment.getPassword(), request.getPassword());
		comment.setStatus(delete);

		return comment;
	}

	public BoardComment findComment(Long id) {
		return commentRepository.findById(id).get();
	}

}
