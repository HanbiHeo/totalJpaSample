package com.kh.totalJpaSample.repository;
import com.kh.totalJpaSample.entity.Cart;
import com.kh.totalJpaSample.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@SpringBootTest //스프링 컨텍스트를 로드하여 테스트 환경 설정. 테스트는 원래 자동 롤백된다. 때문에 DB에 남겨지지 않음. 디비에 저장했던 정보는 다 쓰고나서 지워진다.
@Transactional // 데이터 베이스의 논리적인 작업 단위. [데이터 베이스에서는 transaction은 db관점에서 하나의 명령 수행단위. 내부 동작은 여러개로 쪼개지어 있음. 모두 만족하거나 그렇지 않으면 수행하기 이전 단계로 돌리기](롤백). 트랜젝션과 롤백 개념. 테스트에서 트렌젝션을 걸면 모두 성공해도 롤백
@Slf4j //system.out.println 대신하여 로그 뜨게 하기위해. (로그를 남기기 위해 사용함 = 로깅데이터 수집 및 처리)
@TestPropertySource(locations = "classpath:application-test.properties")
//@RequiredArgsConstructor //생성자로 의존성 주입하기
class CartRepositoryTest {
    @Autowired //테스트는 생성자로 의존성 주입하는게 아니라 어노테이션 필드(Autowired)으로 의존성 주입한다. 객체로 안만들어 지기 때문에.
    CartRepository cartRepository;
    @Autowired
    MemberRepository memberRepository;
    @PersistenceContext //JPA의 EntityManager를 주입받음.
    EntityManager em; //엔티티 메니저 의존성 주입 받음
    //회원 엔티티 생성
    public Member createMemberInfo() { //장바구니와 회원은 조인관계가 있기 때문에 만들고 가야함.  회원객체와 연결을 해놓고 가야함. 이미 만들어진 정보 가져오기.
        Member member = new Member(); //객체지향문법
        member.setUserId("hhb03");
        member.setPassword("1234");
        member.setName("허한비");
        member.setEmail("hhb03@gmail.com");
        member.setRegDate(LocalDateTime.now()); //프론트에서 땡겨서 사용할 수 있음.
        return member;
    }

    @Test
    @DisplayName("장바구니 회원 매핑 테스트")
    public void findCartAndMemberTest() {
        Member member = createMemberInfo();
        memberRepository.save(member); // 회원 한명 만들어짐

        Cart cart = new Cart();
        cart.setCartName("마켓컬리 장바구니");
        cart.setMember(member);
        cartRepository.save(cart);

        em.flush(); // 영속성 컨텍스트에 데이터 저장 후 트랜젝션이 끝날 때 데이터베이스에 기록한다.(엔티티 메니저가 영속성 컨텍스트에 저장 후 디비에 강제로 기록한다-엔티티 메니저 직접 주입받기) - 빨리 확인하고 싶은 경우 사용
        em.clear(); // 영속성 컨텍스트 비우기.
            Cart saveCart = cartRepository.findById(cart.getId()).orElseThrow(EntityNotFoundException::new); //flush했으니 바로 읽힐거임. .orElseThrow(EntityNotFoundException::new) -> 없으면 예외처리 해달라는 부분. (원래는 try catch 구문 걸어주는게 좋다.)
//        log.warn(saveCart.toString());
        log.info(saveCart.toString()); //정보가 들어오는지 확인 할 경우의 info를 찍음.
    }
}