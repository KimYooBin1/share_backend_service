package shared_backend.used_stuff.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shared_backend.used_stuff.dto.board.CommentResponse;
import shared_backend.used_stuff.entity.board.BoardComment;

public interface CommentRepositoryCustom {
	Page<CommentResponse> findBoardComments(Long id, Pageable pageable);

	BoardComment findByCommentId(Long id);
}
