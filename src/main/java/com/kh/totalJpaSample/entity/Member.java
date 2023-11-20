package com.kh.totalJpaSample.entity;

//persistence : 연속성. 영구히 보존되는 특징이 있음.
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
//name 안붙여도 되나, 붙이는것이 관례. 지정 표기법은 카멜표기법이 아님. 소문자로 해야함. 대문자로 해도 테이블은 소문자로 저장됨.
@Table(name = "member")
@Getter @Setter @ToString

//class 는 테이블 이름.('Member' 클래스는 'member' 테이블을 나타냄)
public class Member {

    //Id 이 테이블을 주인. auto는 스프링부트에 따라 자동으로 만들어줌. (시퀀스 테이블 안만들어 주어도 된다.) 'id' 필드는 이 엔티티의 주키(primary key)임
    @Id
    //id의 생성 전략을 맡김. = generation 타입을 auto(Oracle)로하면 id 생성을 위임함. identity(mySQL)로 하면 오류가 날 확률이 있음. 실제 생성은 db에 생성함.
    @GeneratedValue(strategy = GenerationType.AUTO) //주키 값이 자동으로 생성되는 방식을 설정합니다.
    private Long id;

    @Column(nullable = false) // NULL 허용하지 않음
    private String name;
    private String password;

    //unique타입. 제약조건을 이런식으로 걸면 된다.
    @Column(unique = true) // 'email'은 고유한(unique) 값이어야 함
    private String email;
    //join은 mySQL의 키워드이기 때문에 이름 바꿔야함.  'regDate'는 가입일을 나타냄
    private LocalDateTime regDate;

    //데이터가 들어올 때 움직임. 데이터가 영구 저장되기 전(pre-persist)에 실행됨
    @PrePersist
    public void prePersist() {
        regDate = LocalDateTime.now(); //가입일 만들어 낼 때 이런식으로 만들면 됨.  현재 시간을 'regDate'에 할당하여 가입일 생성.
    }
}
