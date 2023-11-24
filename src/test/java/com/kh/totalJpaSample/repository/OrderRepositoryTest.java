package com.kh.totalJpaSample.repository;

import com.kh.totalJpaSample.constant.ItemSellStatus;
import com.kh.totalJpaSample.entity.Item;
import com.kh.totalJpaSample.entity.Member;
import com.kh.totalJpaSample.entity.Order;
import com.kh.totalJpaSample.entity.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
@TestPropertySource(locations="classpath:application-test.properties")
class OrderRepositoryTest {
    @Autowired // 의존성 주입
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository; //서비스 로직과 비슷함. 서비스 혹은 비즈니스 로직 만드는 연습을 여기서 함.
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    OrderItemRepository orderItemRepository;
    @PersistenceContext
    EntityManager em;

    public Item createItem() {
        Item item = new Item(); // new item을 밖에서 빼버리고 for문을 돌리면 하나밖에 안만들어 지기 때문에 안에서 같이 만들어야한다.
        item.setItemName("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        return item;
    }

    @Test// 테스트 코드를 만들고 있지만 실제 로직에서 이런식으로 등록 해야함.
    @DisplayName("영속성 전의 테스트")
    public void cascadeTest() {
        Order order = new Order();
        for(int i = 0; i < 3; i++) {
            Item item = this.createItem(); //위에서 만든 아이템을 만들어야함
            itemRepository.save(item);
            OrderItem orderItem = new OrderItem(); //주문 아이템은 아이템이 있어야 만들 수 있기 때문에 생성
            orderItem.setItem(item); //오더아이템에 아이템 넣기
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItemList().add(orderItem);
        }
        orderRepository.saveAndFlush(order);  //엔티티를 저장하면서 db에 반영. save는 영속성 컨텍스트가 적절한 시점에 실제 db에 반영. 이 시점은 세이브 하자마자 바로 관여. 만들자마자 저장하고 다음단계 바로 테스트 해야하면 세이브 앤 플러쉬.
        em.clear(); //영속성 상태를 초기화.
        //주문 엔티티 조회
        Order saveOrder = orderRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new);//null포인트 방지하기위해 예외처리
        //Order 객체의 OrderItem 개수가 3개인지 확인
        log.warn(String.valueOf(saveOrder.getOrderItemList().size()));
//        assertEquals(3, saveOrder.getOrderItemList().size());
    }

    public Order createOrder() {
        Order order = new Order();
        for(int i = 0; i < 3; i++){
            Item item = createItem(); //createItem을 세번 하기 때문에 new를 세번 하는거와 같음. 다른방법은 매개변수를 만들어서 사용하는 방법이 있음
            itemRepository.save(item); //아이템 3개 만들어서 집어넣기.
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setOrderPrice(1000);
            orderItem.setOrder(order);
            order.getOrderItemList().add(orderItem);
        }
        Member member = new Member();
        member.setName("허한비");
        member.setEmail("hhb03@gmail.com");
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);
        return order;
    }
    @Test
    @DisplayName("고아 객체 제거 테스트")
    public void orphanRemovalTest() {
        Order order = this.createOrder();
        order.getOrderItemList().remove(0);
        em.flush();
    }
    @Test
    @DisplayName("지연로딩 테스트")
    public void lazyLoadingTest() {
        Order order = this.createOrder();
        Long orderItemId = order.getOrderItemList().get(0).getId();
        em.flush();
        em.clear();
        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(EntityNotFoundException::new);//findById자체가 옵셔널처리 되어있기 때문에 널포인트 처리 해주어야함.
        log.warn(String.valueOf(orderItem.getOrder().getClass()));
        log.warn("======================================================================");
        log.warn(String.valueOf(orderItem.getOrder().getOrderDate()));
        log.warn("======================================================================");
    }
}