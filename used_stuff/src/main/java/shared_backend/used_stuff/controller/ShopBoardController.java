package shared_backend.used_stuff.controller;

import static shared_backend.used_stuff.entity.board.Status.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shared_backend.used_stuff.dto.CreateShopBoardRequest;
import shared_backend.used_stuff.dto.ShopBoardResponse;
import shared_backend.used_stuff.entity.shopboard.ShopBoard;
import shared_backend.used_stuff.service.ShopBoardService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ShopBoardController {
	private final ShopBoardService shopBoardService;

	@GetMapping("/shops")
	public Page<ShopBoardResponse> shopBoards(@PageableDefault(size = 10) Pageable pageable,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "search", required = false) String search){	//search 검색 추가
		return shopBoardService.shopBoardList(pageable, type, search);
	}

	// @GetMapping("/shops/{shop_id}/detail")
	// public ShopBoardResponse detailShopBoard(@PathVariable("shop_id") Long id){
	// 	//출력 어떻게 할지
	// 	return new ShopBoardResponse(shopBoardService.findShopBoard(id).getId());
	// }

	@PostMapping("/shops/create")
	public Long createShopBoard(@RequestBody @Valid CreateShopBoardRequest request) {
		ShopBoard shopBoard = shopBoardService.createShopBoard(request);

		return shopBoard.getId();
	}

	@PostMapping("/shops/{shop_id}/edit")
	public Long editShopBoard(@PathVariable("shop_id") Long id) {
		ShopBoard shopBoard = shopBoardService.updateShopBoard(id, edit);
		return shopBoard.getId();
	}

	@PostMapping("/shops/{shop_id}/delete")
	public Long deleteShopBoard(@PathVariable("shop_id") Long id) {
		ShopBoard shopBoard = shopBoardService.updateShopBoard(id, delete);
		return shopBoard.getId();
	}

	//구매하면 판매자에게 돈들어가고 구매자 돈나가고
	@PostMapping("/shops/{shop_id}/purchase")
	public Long purchaseShopBoard(@PathVariable("shop_id") Long id) {
		ShopBoard shopBoard = shopBoardService.orderShopBoard(id, "purchase");

		return shopBoard.getId();
	}
	//취소하면 판매자 돈 줄이고 구매자 돈 돌려주고
	@PostMapping("/shops/{shop_id}/cancel")
	public Long cancelShopBoard(@PathVariable("shop_id") Long id) {
		ShopBoard shopBoard = shopBoardService.orderShopBoard(id, "cancel");

		return shopBoard.getId();
	}
}
