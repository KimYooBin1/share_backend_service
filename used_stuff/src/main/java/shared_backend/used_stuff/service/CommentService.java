package shared_backend.used_stuff.service;

import static java.util.stream.Collectors.*;
import static shared_backend.used_stuff.entity.board.Status.*;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shared_backend.used_stuff.dto.board.CommentResponse;
import shared_backend.used_stuff.dto.board.CreateCommentRequest;
import shared_backend.used_stuff.dto.board.UpdateCommentRequest;
import shared_backend.used_stuff.entity.board.Board;
import shared_backend.used_stuff.entity.board.BoardComment;
import shared_backend.used_stuff.entity.board.Status;
import shared_backend.used_stuff.repository.CommentRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentService extends Check {
	private final CommentRepository commentRepository;
	private final BoardService boardService;
	public List<CommentResponse> comments(Long boardId) {
		List<BoardComment> comments = boardService.findBoardFetchComment(boardId)
			.getBoardComments()
			.stream()
			.filter(comment -> comment.getStatus() != delete)
			.toList();
		return comments.stream().map(comment -> {
			return new CommentResponse(boardId, comment);
		}).collect(toList());
	}
	@Transactional
	public BoardComment createComment(Long boardId, CreateCommentRequest request) {
		Board board = boardService.findOnlyBoard(boardId);
		checkState(board.getStatus());
		BoardComment comment = new BoardComment(request.getWriter(), request.getPassword(), request.getContent());
		board.addComment(comment);
		commentRepository.save(comment);

		return comment;
	}

	@Transactional
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
	@Transactional
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
