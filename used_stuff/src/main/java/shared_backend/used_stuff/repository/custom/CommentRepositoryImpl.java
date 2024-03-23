package shared_backend.used_stuff.repository.custom;

import static shared_backend.used_stuff.entity.board.QBoard.*;
import static shared_backend.used_stuff.entity.board.QBoardComment.*;
import static shared_backend.used_stuff.entity.board.Status.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import shared_backend.used_stuff.dto.board.CommentResponse;
import shared_backend.used_stuff.dto.board.QCommentResponse;
import shared_backend.used_stuff.entity.board.BoardComment;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {
	private final JPAQueryFactory query;
	@Override
	public Page<CommentResponse> findBoardComments(Long id, Pageable pageable) {
		List<CommentResponse> content = query
			.select(new QCommentResponse(boardComment.id, boardComment.writer, boardComment.password,
				boardComment.content, boardComment.status, boardComment.createDate))
			.from(boardComment)
			.where(
				commentStatusNotDelete(),
				commentsBoardIdEq(id)
				)
			.join(boardComment.board, board)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<CommentResponse> countQuery = query
			.select(new QCommentResponse(boardComment.id, boardComment.writer, boardComment.password,
				boardComment.content, boardComment.status, boardComment.createDate))
			.from(boardComment)
			.where();

		return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
	}

	@Override
	public BoardComment findByCommentId(Long id) {
		return query
			.selectFrom(boardComment)
			.where(
				commentStatusNotDelete(),
				boardStatusNotDelete(),
				commentIdEq(id)
			)
			.fetchOne();
	}

	private BooleanExpression commentIdEq(Long id) {
		return boardComment.id.eq(id);
	}

	private BooleanExpression boardStatusNotDelete() {
		return board.status.eq(delete).not();
	}

	private BooleanExpression commentsBoardIdEq(Long id) {
		return boardComment.board.id.eq(id);
	}

	private Predicate commentStatusNotDelete() {
		return boardComment.status.eq(delete).not();
	}

}
