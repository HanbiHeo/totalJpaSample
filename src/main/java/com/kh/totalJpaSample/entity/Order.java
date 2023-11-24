package com.kh.totalJpaSample.entity;

import com.kh.totalJpaSample.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "orders") //order 예약어로 잡힘.
public class Order {
    @Id
    @GeneratedValue // 기본 생성 전략은 Auto
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private  LocalDateTime regTime;
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true) //연관관계의 주인이 아님
    private List<OrderItem> orderItemList = new ArrayList<>(); // 실제 객체가 만들어지게 해 주는것 = 영속성 전의
}
