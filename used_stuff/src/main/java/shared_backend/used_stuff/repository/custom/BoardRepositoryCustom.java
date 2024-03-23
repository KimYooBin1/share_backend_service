package shared_backend.used_stuff.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shared_backend.used_stuff.dto.SearchDto;
import shared_backend.used_stuff.dto.board.BoardListDto;

public interface BoardRepositoryCustom {
	Page<BoardListDto> findBoards(SearchDto search, Pageable pageable);
}
