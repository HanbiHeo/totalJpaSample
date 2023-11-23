package com.kh.totalJpaSample.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter  @Setter
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "order") //mappedBy는 내가 주인이 아니라는 의미. 연관관계의 주인이 아님을 표시한다. manytoone은 객체가 있어야 해당하는게 돌아가서 이름을 넣어주어야 하지만 여기서는 이름을 안만들어 주어도 된다.
    private List<OrderItem> orderItemList = new ArrayList<>(); //만들어진 아이템 정보 불러오기

}
