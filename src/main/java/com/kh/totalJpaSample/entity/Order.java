package com.kh.totalJpaSample.entity;

import com.kh.totalJpaSample.constant.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
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
}
