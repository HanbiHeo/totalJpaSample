package com.kh.totalJpaSample.entity;

import com.kh.totalJpaSample.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
//엔티티는 orm으로 db로 바꾸어줌. 객체 관계 맵핑이 일어남
@Getter
@Setter
@ToString //클래스 내용을 찍어주기 위해 사용
@Entity // primery key를 가져야함. 클래스를 엔티티로 선언하는 키워드.  class문법 = 대문자로 시작
@Table(name = "item") //실제 생성된 테이블의 이름 지정하기. 엔티티와 맵핑할 테이블을 지정. 지정 해주지 않아도 되나, rdbms문법 = 소문자로 시작
public class Item {
    @Id // primery key. 테이블의 기본키를 지정한다.
    @Column(name = "item_id") // 실제 db테이블에는 item_id로 만들어짐. 필드(클래스 내에 있는 것들. 다른 언어에서는 멤버(:필드)라고도 함. 디비에서는 컬럼으로 봄.)와 컬럼을 맵핑함.
    @GeneratedValue(strategy = GenerationType.AUTO) // 생성전략을 자동으로 JPA가 결정.  auto가 제일 무난하게 사용가능하다.
    private Long id; //상품코드

    @Column(nullable = false, length = 50)//null을 허용하지 않고 길이를 50자로 제한.
    private String itemName; //상품명, 이름을 지정하지 않으면 item_name식으로 바뀌어서 표기됨.

    @Column(name="price", nullable = false)
    private int price; //가격

    @Column(nullable = false)
    private int stockNumber; // 재고 수량

    @Lob //큰 데이터를 외부파일로 저장하기 위해 사용. 주로 자막 등에 씀.
    @Column(nullable = false)
    private String itemDetail; //상품 상세 설명

    @Enumerated(EnumType.STRING) // string ; sell, sold-out 자체를 테이블에 저장함. enum으로 정의된 값을 문자열로 db에 저장한다.
    private ItemSellStatus itemSellStatus; //SELL, SOLD_OUT 둘 중 하나.
    private LocalDateTime regTime; //등록시간
    private LocalDateTime updateTime; //수정시간
}
