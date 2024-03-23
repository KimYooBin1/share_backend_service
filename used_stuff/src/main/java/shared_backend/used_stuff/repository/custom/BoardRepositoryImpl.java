package shared_backend.used_stuff.repository.custom;

import static shared_backend.used_stuff.entity.board.QBoard.*;
import static shared_backend.used_stuff.entity.board.Status.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.util.StringUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import shared_backend.used_stuff.dto.SearchDto;
import shared_backend.used_stuff.dto.board.BoardListDto;
import shared_backend.used_stuff.dto.board.QBoardListDto;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

	private final JPAQueryFactory query;

	@Override
	public Page<BoardListDto> findBoards(SearchDto search, Pageable pageable) {
		List<BoardListDto> contents = query
			.select(new QBoardListDto(board.id, board.writer, board.title, board.createDate, board.likes))
			.from(board)
			.where(
				searchTypeEq(search),
				boardStatusNotDelete()
				)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<BoardListDto> countQuery = query
			.select(new QBoardListDto(board.id, board.writer, board.title, board.createDate, board.likes))
			.from(board)
			.where();

		return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchCount);
	}

	private BooleanExpression boardStatusNotDelete() {
		return board.status.eq(delete).not();
	}

	private BooleanExpression searchTypeEq(SearchDto search){
		if(!StringUtils.hasText(search.getType())){
			return null;
		}
		else if(search.getType().equals("writer")){
			return board.writer.contains(search.getSearch());
		}
		else if(search.getType().equals("title")){
			return board.title.contains(search.getSearch());
		}
		else{
			throw new IllegalArgumentException("Invalid type: " + search.getType());
		}
	}

}
