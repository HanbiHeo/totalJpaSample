package com.kh.totalJpaSample.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "cart")
public class Cart {
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String cartName;

    @OneToOne // 회원 엔티티와 일대일 매핑 어노테이션
    @JoinColumn(name = "member_id")//어떤 컬럼과 연결할지 지정해주기  멤버 테이블의 member_id와 이름 같이 해서 연결 => member_id로 프라이빗 키(제약조건이 걸림) 걸어주기.
    private Member member; //cart 장바구니 만들 때 id가 자동으로 생성되고(안넣어줘도 된다.), Member 엔티티에 객체를 넣어줘야함. 나중에 new로 생성해서 넣어줘야 객체가 완성이 됨. = 객체지향 문법이기 때문에
    }
