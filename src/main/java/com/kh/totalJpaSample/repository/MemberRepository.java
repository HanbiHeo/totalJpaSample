package com.kh.totalJpaSample.repository;
//db를 읽고 쓰고 하는건 하나만 있어야 함. 레파지토리 역할을 하는 빈 객체이기 때문에 하나만. 싱글톤으로 등록 된다.
import com.kh.totalJpaSample.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// 데이터베이스와 상호작용하기 위한 레포지토리 인터페이스
// 싱글톤으로 등록되어야 하며, JpaRepository를 상속받음
//JpaRepository로 상속받음 이미 구현체에서 상속바아 다 만들어져 있음.  interface는 완전 추상화. 실제 구현부가 있으면 안됨. 반드시 상속받는애가 구현해줘야함.Hibernate -> JPA구현체가 내부적으로 있으며, 이름을 보고 상속을 받아 찾아줌(구현해줌).
//인터페이스 네이밍 규칙에 의해서 API를 작성하면 그에 맞는 쿼리문을 하이버 네이트(JPA;(orm의 문법을 규정함)의 구현체)가 구현해줌.
public interface MemberRepository  extends JpaRepository<Member, Long> {     // JpaRepository를 상속받아 이미 구현된 메서드를 사용할 수 있음

    // Optional = null을 허용하지 않겠다는 뜻. || findByEmail: 이메일을 기준으로 멤버 객체를 찾는 메서드 || Optional은 값이 없을 수 있는 경우 사용되며, null을 허용하지 않음을 의미 || fintBy 해당하는 멤버 객체를 찾아달라는 쿼리문(select문)을 만듦. 완벽한 카멜 표기법으로 만들어야한다.
    Optional<Member> findByEmail(String email);

    // findByPassword: 비밀번호를 기준으로 멤버 객체를 찾는 메서드
    Member findByPassword(String pwd);

    //join문 만들기.   findByEmailAndPassword: 이메일과 비밀번호를 동시에 기준으로 멤버 객체를 찾는 메서드
    Optional<Member> findByEmailAndPassword(String email, String pwd);

    // existsByEmail: 해당 이메일이 데이터베이스에 존재하는지 여부를 확인하는 메서드
    boolean existsByEmail(String email);
}
