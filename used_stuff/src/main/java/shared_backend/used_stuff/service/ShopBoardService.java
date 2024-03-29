package shared_backend.used_stuff.service;

import static shared_backend.used_stuff.entity.board.Status.*;
import static shared_backend.used_stuff.entity.shopboard.ProductStatus.*;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shared_backend.used_stuff.dto.SearchDto;
import shared_backend.used_stuff.dto.shop.ShopBoardRequest;
import shared_backend.used_stuff.dto.shop.ShopBoardResponse;
import shared_backend.used_stuff.dto.shop.UpdateShopBoardRequest;
import shared_backend.used_stuff.entity.board.Status;
import shared_backend.used_stuff.entity.shopboard.ShopBoard;
import shared_backend.used_stuff.entity.user.User;
import shared_backend.used_stuff.exception.AlreadyDeletedException;
import shared_backend.used_stuff.repository.ShopBoardRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShopBoardService {
	private final ShopBoardRepository shopBoardRepository;
	private final PasswordServiceImpl passwordService;

	//검색, type, sold, sell
	public Page<ShopBoardResponse> shopBoardList(Pageable pageable, SearchDto search){
		return shopBoardRepository.findShopSearchList(search.getType(), search.getSearch(),
			pageable)
			.map(o -> new ShopBoardResponse(o.getId(), o.getTitle(), o.getUser().getProfile().getName(),
				o.getBuyer() == null ? "" : o.getBuyer().getProfile().getName(),
				o.getProductStatus(), o.getCreateDate()));
	}

	/**
	 * board Detail
	 */
	public ShopBoard findShopBoard(Long id){
		// TODO : 뭔데
		ShopBoard findBoard = shopBoardRepository.findBoardWithFetch(id).orElseThrow(
			() -> new NullPointerException("")
		);
		if(findBoard.getStatus() == delete){
			throw new AlreadyDeletedException("이미 삭제된 게시글입니다");
		}
		return findBoard;
	}

	public Page<ShopBoardResponse> findOrderListByName(String name, SearchDto search, Pageable pageable){
		return shopBoardRepository.findOrderSearchList(name, search.getType(),search.getSearch(), pageable)
			.map(o -> new ShopBoardResponse(o.getId(), o.getTitle(), o.getUser().getProfile().getName(),
				o.getBuyer() == null ? "" : o.getBuyer().getProfile().getName(),
				o.getProductStatus(), o.getCreateDate()));
	}

	@Transactional
	public ShopBoard createShopBoard(ShopBoardRequest request){
		User user = passwordService.findUser();
		//현재 user 만 뽑아오고 싶지만 실제로는 user 연관 entity를 모두 조회하기 때문에 성능이 떨어진다. 수정 필요
		ShopBoard shopBoard = new ShopBoard(request, user);
		shopBoardRepository.save(shopBoard);
		return shopBoard;
	}
	@Transactional
	public ShopBoard updateShopBoard(Long id, Status status, UpdateShopBoardRequest request){
		// TODO : update시 데이터 변경 확인에 관해 testcode 추가 작성
		ShopBoard findBoard = checkStatusAndAccess(id, status);
		findBoard.updateShopBoard(request);
		return findBoard;
	}

	@Transactional
	public ShopBoard deleteShopBoard(Long id, Status status) {
		return checkStatusAndAccess(id, status);
	}

	@Transactional
	public ShopBoard orderShopBoard(Long id, String type){
		//TODO : Optional 처리하고 repositorylmpl 에 type을 전달해서 각각 조건에 맞는 query 작성하기
		// user는 따로 필요하기 때뮨에 query를 분리하는게 맞나?
		// test 작성하기
		ShopBoard findBoard = shopBoardRepository.findBoardWithFetch(id).orElseThrow(
			() -> new NullPointerException("삭제된 게시글입니다")
		);

		User user = passwordService.findUser();

		if(Objects.equals(type, "purchase")){
			if(findBoard.getProductStatus() == sold){	//추가적인 exception 추가하기
				throw new RuntimeException("이미 판매된 게시글입니다");
			}
			if(findBoard.getUser() == user){
				throw new RuntimeException("본인 상품 입니다");
			}
			if(user.getPoint() < findBoard.getPrice()){
				throw new RuntimeException("포인트가 부족합니다");
			}
			findBoard.purchase(user);
		}
		else{
			if(findBoard.getBuyer() != user){	//추가 exception 필요
				throw new RuntimeException("구매자가 일치하지 않습니다");
			}
			if(findBoard.getUser().getPoint() < findBoard.getPrice()){
				throw new RuntimeException("환불 금액이 부족합니다");
			}
			findBoard.cancel(user);
		}
		return findBoard;
	}

	private ShopBoard checkStatusAndAccess(Long id, Status status) throws NullPointerException{

		ShopBoard findBoard = shopBoardRepository.findBoardWithAuth(id,
			SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(
			() -> new NullPointerException("삭제되었거나 권한이 없습니다")
		);
		findBoard.statusChange(status);
		return findBoard;
	}


}
