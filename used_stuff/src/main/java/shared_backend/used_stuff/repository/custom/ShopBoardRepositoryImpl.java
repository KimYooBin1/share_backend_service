package shared_backend.used_stuff.repository.custom;

import static org.springframework.util.StringUtils.*;
import static shared_backend.used_stuff.entity.shopboard.ProductStatus.*;
import static shared_backend.used_stuff.entity.shopboard.QShopBoard.*;
import static shared_backend.used_stuff.entity.user.QPassword.*;
import static shared_backend.used_stuff.entity.user.QProfile.*;
import static shared_backend.used_stuff.entity.user.QUser.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import shared_backend.used_stuff.entity.board.Status;
import shared_backend.used_stuff.entity.shopboard.ShopBoard;
import shared_backend.used_stuff.entity.user.QPassword;
import shared_backend.used_stuff.entity.user.QProfile;
import shared_backend.used_stuff.entity.user.QUser;

@RequiredArgsConstructor
public class ShopBoardRepositoryImpl implements ShopBoardRepositoryCustom {
	private final JPAQueryFactory query;

	@Override
	public Page<ShopBoard> findOrderSearchList(String name, String type, String search, Pageable pageable) {
		QUser buyer = new QUser("buyer");
		QProfile buyerProfile = new QProfile("buyerProfile");
		QPassword buyerPassword = new QPassword("buyerPassword");

		List<ShopBoard> contents = query
			.selectFrom(shopBoard)
			.join(shopBoard.user, user).fetchJoin()
			.join(user.password, password).fetchJoin()
			.join(user.profile, profile).fetchJoin()
			.leftJoin(shopBoard.buyer, buyer).fetchJoin()
			.leftJoin(buyer.password, buyerPassword).fetchJoin()
			.leftJoin(buyer.profile, buyerProfile).fetchJoin()
			.where(shopBoard.status.eq(Status.delete).not(),
				titleContain(search),
				userEq(type, name))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<ShopBoard> countQuery = query
			.selectFrom(shopBoard)
			.join(shopBoard.user, user).fetchJoin()
			.join(user.password, password).fetchJoin()
			.join(user.profile, profile).fetchJoin()
			.leftJoin(shopBoard.buyer, buyer).fetchJoin()
			.leftJoin(buyer.password, buyerPassword).fetchJoin()
			.leftJoin(buyer.profile, buyerProfile).fetchJoin()
			.where(shopBoard.status.eq(Status.delete).not(),
				titleContain(search),
				userEq(type, name));


		return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchCount);
	}

	@Override
	public Page<ShopBoard> findShopSearchList(String type, String search, Pageable pageable) {
		QUser buyer = new QUser("buyer");
		QProfile buyerProfile = new QProfile("buyerProfile");
		QPassword buyerPassword = new QPassword("buyerPassword");
		List<ShopBoard> contents = query
			.selectFrom(shopBoard)
			.join(shopBoard.user, user).fetchJoin()
			.join(user.password, password).fetchJoin()
			.join(user.profile, profile).fetchJoin()
			.leftJoin(shopBoard.buyer, buyer).fetchJoin()
			.leftJoin(buyer.password, buyerPassword).fetchJoin()
			.leftJoin(buyer.profile, buyerProfile).fetchJoin()
			.where(shopBoard.status.eq(Status.delete).not(), productStatusEq(type), titleContain(search))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<ShopBoard> countQuery = query
			.selectFrom(shopBoard)
			.join(shopBoard.user, user).fetchJoin()
			.join(user.password, password).fetchJoin()
			.join(user.profile, profile).fetchJoin()
			.leftJoin(shopBoard.buyer, buyer).fetchJoin()
			.leftJoin(buyer.password, buyerPassword).fetchJoin()
			.leftJoin(buyer.profile, buyerProfile).fetchJoin()
			.where(shopBoard.status.eq(Status.delete).not(), productStatusEq(type), titleContain(search));

		return PageableExecutionUtils.getPage(contents, pageable, countQuery::fetchCount);

	}

	public BooleanExpression productStatusEq(String type){
		if (!hasText(type)) {
			return null;
		} else if (type.equals("sell")) {
			return shopBoard.productStatus.eq(sell);
		} else if (type.equals("sold")) {
			return shopBoard.productStatus.eq(sold);
		} else {
			throw new IllegalArgumentException("Invalid type: " + type);
		}
	}

	public BooleanExpression titleContain(String title){
		return hasText(title) ? shopBoard.title.contains(title) : null;
	}

	public BooleanExpression userEq(String type, String name){
		QPassword buyerPassword = new QPassword("buyerPassword");
		if (!hasText(type)) {
			return buyerPassword.username.eq(name).or(password.username.eq(name));
		} else if (type.equals("sell")) {
			return password.username.eq(name);
		} else if (type.equals("buy")) {
			return buyerPassword.username.eq(name);
		} else {
			throw new IllegalArgumentException("Invalid type: " + type);
		}
	}
}
