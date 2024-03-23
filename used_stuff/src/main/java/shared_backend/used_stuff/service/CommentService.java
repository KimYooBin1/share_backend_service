package shared_backend.used_stuff.service;

import static shared_backend.used_stuff.entity.board.Status.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shared_backend.used_stuff.dto.board.CommentResponse;
import shared_backend.used_stuff.dto.board.CreateCommentRequest;
import shared_backend.used_stuff.dto.board.UpdateCommentRequest;
import shared_backend.used_stuff.entity.board.Board;
import shared_backend.used_stuff.entity.board.BoardComment;
import shared_backend.used_stuff.repository.CommentRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class CommentService extends Check {
	private final CommentRepository commentRepository;
	private final BoardService boardService;

	public Page<CommentResponse> comments(Long boardId, Pageable pageable) {
		return commentRepository.findBoardComments(boardId, pageable);
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
		// TODO : 예외를 다른 방법으로 처리하기.
		BoardComment comment = commentRepository.findByCommentId(commentId);
		if(comment == null){
			throw new RuntimeException("잘못된 접근입니다");
		}
		checkPW(comment.getPassword(), request.getPassword());
		// TODO : test시 문제 발생
		comment.editComment(request.getContent(), edit);

		return comment;
	}
	@Transactional
	public BoardComment deleteComment(Long commentId, UpdateCommentRequest request){
		// TODO : 예외를 다른 방법으로 처리하기.
		BoardComment comment = commentRepository.findByCommentId(commentId);
		if(comment == null){
			throw new RuntimeException("잘못된 접근입니다");
		}
		checkPW(comment.getPassword(), request.getPassword());
		comment.deleteComment(delete);

		return comment;
	}

	public BoardComment findComment(Long id) {
		return commentRepository.findByCommentId(id);
	}

}
