package com.kh.totalJpaSample.repository;
import com.kh.totalJpaSample.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    //JPQL(Jakarta Persistence Query Language)쿼리 작성 하기 : SQL과 유사한 객체지향 쿼리 언어(실제 DB문으로 날리는게 아님)이다. 완벽한 SQL은 아님. 문법도 sql과 유사하지만 sql이 아님. db로 날리는게 아닌 entity로 날림. 데이터 베이스 종속적이지 않고 엔티티 클래스와 필드를 기반으로 쿼리를 작성할 수 있다.
    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> priceSorting(@Param("itemDetail") String itemDetail);


    //native Query 사용하기 : 실제 db에 사용할거라 item 소문자. 특정 데이터베이스에 종속적인 SQL을 사용하여 직접 작성된다. 이는 엔티티가 아닌 실제 데이터베이스 테이블과 컬럼을 대상으로 쿼리를 작성할 수 있게 한다.
    @Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price desc", //실제 sql문을 날릴꺼임.
    nativeQuery = true)
    List<Item> priceSortingNative(@Param("itemDetail") String itemDetail);
}
