package shared_backend.used_stuff.service;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shared_backend.used_stuff.dto.CreateShopBoardRequest;
import shared_backend.used_stuff.dto.ShopBoardResponse;
import shared_backend.used_stuff.entity.board.Status;
import shared_backend.used_stuff.entity.shopboard.ShopBoard;
import shared_backend.used_stuff.entity.user.User;
import shared_backend.used_stuff.repository.ShopBoardRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopBoardService {
	private final ShopBoardRepository shopBoardRepository;
	private final PasswordServiceImpl passwordService;

	public Page<ShopBoardResponse> shopBoardList(Pageable pageable){
		return shopBoardRepository.findAll(pageable).map(shop -> new ShopBoardResponse(shop.getId()));
	}

	public ShopBoard findShopBoard(Long id) {
		return shopBoardRepository.findById(id).get();
	}

	public ShopBoard createShopBoard(CreateShopBoardRequest request){
		User user = passwordService.findUser();
		//현재 user 만 뽑아오고 싶지만 실제로는 user 연관 entity를 모두 조회하기 때문에 성능이 떨어진다. 수정 필요
		ShopBoard shopBoard = new ShopBoard(request, user);
		shopBoardRepository.save(shopBoard);
		return shopBoard;
	}

	public ShopBoard updateShopBoard(Long id, Status status){
		User user = passwordService.findUser();
		ShopBoard findBoard = shopBoardRepository.findById(id).get();
		if(!Objects.equals(user.getId(), findBoard.getUser().getId())){
			throw new RuntimeException("권한이 없습니다");
		}
		//이미 삭제된 board 에 대해 추가적인 처리 필요
		findBoard.setStatus(status);
		return findBoard;
	}
	public ShopBoard orderShopBoard(Long id, String type){
		ShopBoard findBoard = shopBoardRepository.findById(id).get();
		User user = passwordService.findUser();
		if(Objects.equals(type, "purchase")){
			findBoard.purchase(user);
		}
		else{
			findBoard.cancel(user);
		}
		return findBoard;
	}
}
