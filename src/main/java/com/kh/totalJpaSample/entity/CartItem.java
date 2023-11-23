package com.kh.totalJpaSample.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_item_id")
    private Long id;
    @ManyToOne //다대일 관계, 하나의 장바구니에는 여러개의 상품을 담을 수 있음. cart_item의 입장에서는 여러개의 매핑이 들어옴
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item; //다대일 관계, 하나의 아이템은 여러 장바구니에 아이템으로 담김 (one이 없으면 many는 만들어질 수 없다고 생각하면 조금 더 쉽게 이해 가능)
    private int count;

}
