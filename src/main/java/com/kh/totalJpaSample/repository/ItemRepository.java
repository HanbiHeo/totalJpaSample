package com.kh.totalJpaSample.repository;
import com.kh.totalJpaSample.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//기본적인 CRUD는 JpaRepository에서 상속받아 이미 정의되어 있음. 페이징 처리도 포함되어 있음.
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item>  findByItemName(String itemName);
    //OR 조건 처리
    List<Item> findByItemNameOrItemDetail(String itemName, String itemDetail);
    //LessThan 조건 처리 (보다 작다) ; 가격(price 변수)보다 작은 상품 데이터 조회 쿼리. 미만
    List<Item> findByPriceLessThan(Integer price);
    //OrderBy로 정렬하기
    List<Item> findAllByOrderByPriceDesc();
}
