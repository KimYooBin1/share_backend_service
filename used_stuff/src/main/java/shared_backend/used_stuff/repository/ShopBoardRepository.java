package shared_backend.used_stuff.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shared_backend.used_stuff.entity.board.Status;
import shared_backend.used_stuff.entity.shopboard.ProductStatus;
import shared_backend.used_stuff.entity.shopboard.ShopBoard;
import shared_backend.used_stuff.repository.custom.ShopBoardRepositoryCustom;

@Repository
public interface ShopBoardRepository extends JpaRepository<ShopBoard, Long>, ShopBoardRepositoryCustom {
	@EntityGraph("shopBoard-with-user-and-profile")
	Page<ShopBoard> findAllByProductStatusAndStatusNot(ProductStatus productStatus, Status status, Pageable pageable);
	@EntityGraph("shopBoard-with-user-and-profile")
	Page<ShopBoard> findAllByTitleContainingAndProductStatusAndStatusNot(String title, ProductStatus productStatus, Status status,Pageable pageable);

	@EntityGraph("shopBoard-with-user-and-profile")
	Page<ShopBoard> findAllByTitleContainingAndStatusNot(String title, Status status, Pageable pageable);
	@EntityGraph("shopBoard-with-user-and-profile")
	Page<ShopBoard> findAllByStatusNot(Status status, Pageable pageable);

	@EntityGraph("shopBoard-with-user-and-profile")
	Optional<ShopBoard> findById(Long id);
}
