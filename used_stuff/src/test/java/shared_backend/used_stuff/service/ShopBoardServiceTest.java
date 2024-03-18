package shared_backend.used_stuff.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static shared_backend.used_stuff.entity.board.Status.*;
import static shared_backend.used_stuff.entity.shopboard.ProductStatus.*;
import static shared_backend.used_stuff.entity.user.Gender.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import shared_backend.used_stuff.dto.shop.ShopBoardRequest;
import shared_backend.used_stuff.dto.shop.ShopBoardResponse;
import shared_backend.used_stuff.dto.shop.UpdateShopBoardRequest;
import shared_backend.used_stuff.entity.Address;
import shared_backend.used_stuff.entity.shopboard.ShopBoard;
import shared_backend.used_stuff.entity.user.Password;
import shared_backend.used_stuff.entity.user.Profile;
import shared_backend.used_stuff.entity.user.User;
import shared_backend.used_stuff.exception.AlreadyDeletedException;

@SpringBootTest
@Transactional
class ShopBoardServiceTest {
	@Autowired
	EntityManager em;
	@Autowired
	ShopBoardService shopBoardService;
	@Autowired
	UserService userService;


	@Test
	@WithMockUser
	@DisplayName("중고 물품 조회")
	public void 물품_조회(){
		createUser();
		for(int i=0;i<5;i++){
			createBoard();
		}
		em.flush();
		em.clear();

		PageRequest page = PageRequest.of(0, 5);
		Page<ShopBoardResponse> findPage = shopBoardService.shopBoardList(page, null, null);
		assertThat(findPage.getTotalElements()).isEqualTo(5);
	}

	@Test
	@WithMockUser
	@DisplayName("중고 물품 등록")
	public void 물품_등록() {
		createUser();
		ShopBoard shopBoard = createBoard();

		em.flush();
		em.clear();

		ShopBoard findBoard = shopBoardService.findShopBoard(shopBoard.getId());
		assertThat(findBoard.getId()).isEqualTo(shopBoard.getId());
	}

	@Test
	@WithMockUser
	@DisplayName("중고 물품 수정")
	public void 물품_수정() {
		createUser();
		ShopBoard shopBoard = createBoard();
		em.flush();
		em.clear();
		//지금은 status만 변하지만 나중에는 실제 내용이 바뀌는지에 대해 test
		UpdateShopBoardRequest request = new UpdateShopBoardRequest("title", "content", "url", 1000,
			new Address("zipcode", "address", "addressDetail"));
		ShopBoard editBoard = shopBoardService.updateShopBoard(shopBoard.getId(), edit, request);
		assertThat(editBoard.getStatus()).isEqualTo(edit);
	}

	@Test
	@WithMockUser
	@DisplayName("중고 물품 삭제")
	public void 물품_삭제() {
		createUser();
		ShopBoard shopBoard = createBoard();
		em.flush();
		em.clear();
		ShopBoard deleteBoard = shopBoardService.deleteShopBoard(shopBoard.getId(), delete);
		assertThat(deleteBoard.getStatus()).isEqualTo(delete);
	}

	@Test
	@WithMockUser
	@DisplayName("중고 물품 삭제 에러")
	public void 물품_삭제_에러() {
		createUser();
		ShopBoard shopBoard = createBoard();
		em.flush();
		em.clear();
		ShopBoard deleteBoard = shopBoardService.deleteShopBoard(shopBoard.getId(), delete);
		assertThrows(AlreadyDeletedException.class, ()->shopBoardService.findShopBoard(deleteBoard.getId()));
		assertThrows(AlreadyDeletedException.class, ()->shopBoardService.deleteShopBoard(deleteBoard.getId(), delete));
	}

	@Test
	@WithMockUser
	@DisplayName("권한 없음")
	public void 권한_없음(){
		createUser();
		User user = createUser("user2");
		ShopBoard board = createBoard();
		board.testUserChange(user);
		em.flush();
		em.clear();
		UpdateShopBoardRequest request = new UpdateShopBoardRequest("title", "content", "url", 1000,
			new Address("zipcode", "address", "addressDetail"));
		assertThrows(AccessDeniedException.class, ()->shopBoardService.deleteShopBoard(board.getId(), delete));
		assertThrows(AccessDeniedException.class, ()->shopBoardService.updateShopBoard(board.getId(), edit, request));
	}

	@Test
	@WithMockUser
	@DisplayName("물품 구매")
	public void 물품_구매(){
		createUser();
		User user = createUser("user2");
		ShopBoard board = createBoard();
		board.testUserChange(user);
		em.flush();
		em.clear();

		ShopBoard shopBoard = shopBoardService.orderShopBoard(board.getId(), "purchase");
		assertThat(shopBoard.getProductStatus()).isEqualTo(sold);
	}

	@Test
	@WithMockUser
	@DisplayName("본인_물품 구매")
	public void 본인_물품_구매(){
		createUser();
		User user = createUser("user2");
		ShopBoard board = createBoard();
		em.flush();
		em.clear();
		assertThrows(RuntimeException.class, () ->
			shopBoardService.orderShopBoard(board.getId(), "purchase")
		);
	}

	@Test
	@WithMockUser
	@DisplayName("물품 중복 구매")
	public void 물품_증복_구매(){
		createUser();
		User user = createUser("user2");
		ShopBoard board = createBoard();
		board.testUserChange(user);
		em.flush();
		em.clear();

		shopBoardService.orderShopBoard(board.getId(), "purchase");
		assertThrows(AlreadyDeletedException.class, () ->
			shopBoardService.orderShopBoard(board.getId(), "purchase")
		);
	}

	@Test
	@WithMockUser
	@DisplayName("구매 취소")
	public void 구매_취소(){
		createUser();
		User user = createUser("user2");
		ShopBoard board = createBoard();
		board.testUserChange(user);
		em.flush();
		em.clear();

		ShopBoard shopBoard = shopBoardService.orderShopBoard(board.getId(), "purchase");
		assertThat(shopBoard.getProductStatus()).isEqualTo(sold);
		em.flush();
		em.clear();

		ShopBoard shopBoard1 = shopBoardService.orderShopBoard(board.getId(), "cancel");
		assertThat(shopBoard1.getProductStatus()).isEqualTo(sell);
	}

	@Test
	@WithMockUser
	@DisplayName("구매 취소 실패")
	public void 물품_취소_실패(){
		createUser();
		User user = createUser("user2");
		ShopBoard board = createBoard();
		board.testBuyerChange(user);
		em.flush();
		em.clear();

		assertThrows(RuntimeException.class, () ->
			shopBoardService.orderShopBoard(board.getId(), "cancel")
		);
	}


	private ShopBoard createBoard() {
		ShopBoardRequest request = new ShopBoardRequest("title", "content", "url", 1000,
			new Address("zipecode", "address", "addressDetail"));

		return shopBoardService.createShopBoard(request);
	}

	private User createUser() {
		Password password = new Password("user", "password", "user");
		Profile profile = new Profile("name", 10, female, new Address("zipcode", "address", "addressDetail"));

		return userService.createUser(password, profile);
	}
	private User createUser(String username) {
		Password password = new Password(username, "password", "user");
		Profile profile = new Profile("name", 10, female, new Address("zipcode", "address", "addressDetail"));
		return userService.createUser(password, profile);
	}
}
