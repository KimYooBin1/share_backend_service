package shared_backend.used_stuff.service;

import static java.time.LocalDateTime.*;
import static java.util.stream.Collectors.*;
import static shared_backend.used_stuff.entity.board.Status.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import shared_backend.used_stuff.dto.BoardResponse;
import shared_backend.used_stuff.dto.CreateBoardRequest;
import shared_backend.used_stuff.dto.UpdateBoardRequest;
import shared_backend.used_stuff.entity.board.Board;
import shared_backend.used_stuff.repository.BoardRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService extends Check {
	private final BoardRepository boardRepository;

	public List<BoardResponse> boards(int page, HttpServletResponse response) throws IOException {
			int totalCount = boardRepository.findAll().size();
			int all_page = ((totalCount - 1) / 10);
			if(all_page < page){
				page = all_page;
				String url = "/boards?page=" + page;
				response.sendRedirect(url);
			}
			PageRequest pageRequest = PageRequest.of(page, 10);

			return boardRepository.findAll(pageRequest).stream().map(BoardResponse::new).collect(toList());
	}

	public List<BoardResponse> bestBoards() {
		PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("likes").descending());

		return boardRepository.findAll(pageRequest).stream().map(BoardResponse::new).collect(toList());
	}

	public Board findBoard(Long id){
		return boardRepository.findById(id).get();
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
		if(request.getTitle() != null){
			board.setTitle(request.getTitle());
		}
		if (request.getContent() != null) {
			board.setContent(request.getContent());
		}
		board.setStatus(edit);
		board.setUpdateDate(now());

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
}
