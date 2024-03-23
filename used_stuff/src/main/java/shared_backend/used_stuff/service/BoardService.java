package shared_backend.used_stuff.service;

import static org.springframework.util.StringUtils.*;
import static shared_backend.used_stuff.entity.board.Status.*;

import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shared_backend.used_stuff.dto.board.CreateBoardRequest;
import shared_backend.used_stuff.dto.SearchDto;
import shared_backend.used_stuff.dto.board.UpdateBoardRequest;
import shared_backend.used_stuff.dto.board.BoardListDto;
import shared_backend.used_stuff.entity.board.Board;
import shared_backend.used_stuff.repository.BoardRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService extends Check {
	private final BoardRepository boardRepository;

	public Page<BoardListDto> boardList(SearchDto search, Pageable pageable) {
		//TODO : sort 조건 추가
		return boardRepository.findBoards(search, pageable);
	}

	public List<BoardListDto> bestBoards() {
		PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("likes").descending());

		return boardRepository.findAllProjectedByLikesGreaterThan(0, pageRequest);
	}

	public Board findBoard(Long id){
		return boardRepository.findById(id).get();
	}

	public Board findBoardFetchComment(Long id){
		return boardRepository.findBoardFetchComments(id);

	}

	@Transactional
	public Board createBoard(CreateBoardRequest request) {
		Board board = new Board(request.getWriter(), request.getPassword(), request.getTitle(), request.getContent());
		boardRepository.save(board);

		return board;
	}

	@Transactional
	public Board editBoard(Long id, UpdateBoardRequest request) {
		Board board = boardRepository.findById(id).get();
		checkPW(board.getPassword(), request.getPassword());
		checkState(board.getStatus());
		if(hasText(request.getTitle())){
			board.setTitle(request.getTitle());
		}
		if (hasText(request.getContent())) {
			board.setContent(request.getContent());
		}
		board.setStatus(edit);

		return board;
	}

	@Transactional
	public Board deleteBoard(Long id, UpdateBoardRequest request) {
		Board board = boardRepository.findById(id).get();
		checkPW(board.getPassword(), request.getPassword());
		checkState(board.getStatus());
		board.setStatus(delete);

		return board;
	}

	@Transactional
	public Board likeBoard(Long id, String type) {
		Board board = boardRepository.findById(id).get();
		checkState(board.getStatus());
		if(Objects.equals(type, "like")){
			board.likeBoard();
		}else{
			board.dislikeBoard();
		}
		return board;
	}

	public Board findOnlyBoard(Long boardId) {
		return boardRepository.findById(boardId).get();
	}
}
