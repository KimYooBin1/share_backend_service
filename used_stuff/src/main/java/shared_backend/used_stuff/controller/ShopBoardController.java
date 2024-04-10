package shared_backend.used_stuff.controller;

import static shared_backend.used_stuff.entity.board.Status.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shared_backend.used_stuff.dto.SearchDto;
import shared_backend.used_stuff.dto.shop.ShopBoardRequest;
import shared_backend.used_stuff.dto.user.IdResponse;
import shared_backend.used_stuff.dto.shop.ShopBoardDetailResponse;
import shared_backend.used_stuff.dto.shop.ShopBoardResponse;
import shared_backend.used_stuff.dto.shop.UpdateShopBoardRequest;
import shared_backend.used_stuff.service.ShopBoardService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ShopBoardController {
	private final ShopBoardService shopBoardService;

	@GetMapping("/shops")
	public ResponseEntity<Page<ShopBoardResponse>> shopBoards(@PageableDefault(size = 10) Pageable pageable, SearchDto search) {
		return ResponseEntity.ok(shopBoardService.shopBoardList(pageable, search));
	}

	@GetMapping("/shops/{shop_id}/detail")
	public ResponseEntity<ShopBoardDetailResponse> detailShopBoard(@PathVariable("shop_id") Long id){
		return ResponseEntity.ok(new ShopBoardDetailResponse(shopBoardService.findShopBoard(id)));
	}

	@PostMapping("/shops/create")
	public ResponseEntity<IdResponse> createShopBoard(@RequestBody @Valid ShopBoardRequest request) {
		return ResponseEntity.ok(new IdResponse(shopBoardService.createShopBoard(request).getId()));
	}

	@PostMapping("/shops/{shop_id}/edit")
	public ResponseEntity<IdResponse> editShopBoard(@PathVariable("shop_id") Long id,
		@RequestBody @Valid UpdateShopBoardRequest request) {
		return ResponseEntity.ok(new IdResponse(shopBoardService.updateShopBoard(id, edit, request).getId()));
	}

	@PostMapping("/shops/{shop_id}/delete")
	public ResponseEntity<IdResponse> deleteShopBoard(@PathVariable("shop_id") Long id) {
		return ResponseEntity.ok(new IdResponse(shopBoardService.deleteShopBoard(id, delete).getId()));
	}

	@PostMapping("/shops/{shop_id}/purchase")
	public ResponseEntity<IdResponse> purchaseShopBoard(@PathVariable("shop_id") Long id) {
		return ResponseEntity.ok(new IdResponse(shopBoardService.orderShopBoard(id, "purchase").getId()));
	}

	@PostMapping("/shops/{shop_id}/cancel")
	public ResponseEntity<IdResponse> cancelShopBoard(@PathVariable("shop_id") Long id) {
		return ResponseEntity.ok(new IdResponse(shopBoardService.orderShopBoard(id, "cancel").getId()));
	}
}
