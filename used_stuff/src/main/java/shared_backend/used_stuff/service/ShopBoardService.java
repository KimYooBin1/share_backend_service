package shared_backend.used_stuff.service;

import static shared_backend.used_stuff.entity.board.Status.*;
import static shared_backend.used_stuff.entity.shopboard.ProductStatus.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
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
	public Page<ShopBoardResponse> shopBoardList(Pageable pageable, String type, String search){
		if(search==null){
			if(Objects.equals(type, "sold")){
				return shopBoardRepository.findAllByProductStatusAndStatusNot(sold, delete, pageable)
					.map(s -> new ShopBoardResponse(s.getId(), s.getTitle(), s.getUser().getProfile().getName(),
						s.getBuyer().getProfile().getName(), s.getProductStatus(), s.getCreateDate()));
			}
			else if(Objects.equals(type, "sell")){
				return shopBoardRepository.findAllByProductStatusAndStatusNot(sell,delete, pageable)
					.map(s -> new ShopBoardResponse(s.getId(), s.getTitle(), s.getUser().getProfile().getName(),
						s.getProductStatus(), s.getCreateDate()));
			}
			else{
				return shopBoardRepository.findAllByStatusNot(delete, pageable).map(s -> new ShopBoardResponse(s.getId(), s.getTitle(), s.getUser().getProfile().getName(),
					s.getProductStatus(), s.getCreateDate()));
			}
		}
		else{
			if(Objects.equals(type, "sold")){
				return shopBoardRepository.findAllByTitleContainingAndProductStatusAndStatusNot(search, sold, delete,
						pageable)
					.map(s -> new ShopBoardResponse(s.getId(), s.getTitle(), s.getUser().getProfile().getName(),
						s.getBuyer().getProfile().getName(), s.getProductStatus(), s.getCreateDate()));
			}
			else if(Objects.equals(type, "sell")){
				return shopBoardRepository.findAllByTitleContainingAndProductStatusAndStatusNot(search, sell, delete,
						pageable)
					.map(s -> new ShopBoardResponse(s.getId(), s.getTitle(), s.getUser().getProfile().getName(),
						s.getProductStatus(), s.getCreateDate()));
			}
			else{
				return shopBoardRepository.findAllByTitleContainingAndStatusNot(search, delete, pageable)
					.map(s -> new ShopBoardResponse(s.getId(), s.getTitle(), s.getUser().getProfile().getName(),
						s.getProductStatus(), s.getCreateDate()));
			}
		}
	}

	public ShopBoard findShopBoard(Long id) {
		ShopBoard findBoard = shopBoardRepository.findById(id).get();
		if(findBoard.getStatus() == delete){
			throw new AlreadyDeletedException("이미 삭제된 게시글입니다");
		}
		return findBoard;
	}

	public List<ShopBoardResponse> findOrderListByName(String name, String type, String search){
		List<ShopBoard> orderListByName = shopBoardRepository.findOrderSearchList(name, type, search);
		return orderListByName.stream()
			.map(o -> new ShopBoardResponse(o.getId(), o.getTitle(), o.getUser().getProfile().getName(),
				o.getBuyer() == null ? "" : o.getBuyer().getProfile().getName(),
				o.getProductStatus(), o.getCreateDate())).collect(
				Collectors.toList());
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
		//update시 데이터 변경 확인에 관해 testcode 추가 작성
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
		//TODO : findById 와 findUser 두번의 query 접근발생.
		ShopBoard findBoard = shopBoardRepository.findById(id).get();

		if(findBoard.getStatus() == delete){
			throw new AlreadyDeletedException("이미 삭제된 게시글입니다");
		}
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

	private ShopBoard checkStatusAndAccess(Long id, Status status) {
		User user = passwordService.findUser();
		ShopBoard findBoard = shopBoardRepository.findById(id).get();
		if(findBoard.getStatus() == delete){
			throw new AlreadyDeletedException("이미 삭제된 게시글입니다");
		}
		if(!Objects.equals(user.getId(), findBoard.getUser().getId())){
			throw new AccessDeniedException("권한이 없습니다");
		}
		findBoard.statusChange(status);
		return findBoard;
	}


}
