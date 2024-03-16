package shared_backend.used_stuff.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shared_backend.used_stuff.entity.shopboard.ProductStatus;
import shared_backend.used_stuff.entity.shopboard.ShopBoard;

@Repository
public interface ShopBoardRepository extends JpaRepository<ShopBoard, Long> {
	@EntityGraph("shopBoard-with-user-and-profile")
	Page<ShopBoard> findAllByProductStatus(ProductStatus status, Pageable pageable);
	@EntityGraph("shopBoard-with-user-and-profile")
	Page<ShopBoard> findAllByTitleContainingAndProductStatus(String title, ProductStatus status, Pageable pageable);
	@EntityGraph("shopBoard-with-user-and-profile")
	Page<ShopBoard> findAllByTitleContaining(String title, Pageable pageable);
	@EntityGraph("shopBoard-with-user-and-profile")
	Page<ShopBoard> findAll(Pageable pageable);


}
