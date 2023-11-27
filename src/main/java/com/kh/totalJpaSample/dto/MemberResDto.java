package com.kh.totalJpaSample.dto;

import com.kh.totalJpaSample.entity.Member;
import lombok.*;

import java.time.LocalDateTime;

//getter setter 는 필드값을 핸들링 할 때 게터 세터로 캡슐화. 객체지향 문법의 특성.
// @Getter와 @Setter는 Lombok 라이브러리를 통해 자동으로 Getter와 Setter 메서드를 생성하도록 지정함.
// 이렇게 하면 객체의 필드에 접근하고 값을 설정하는 메서드를 별도로 작성하지 않고도 간편하게 사용할 수 있다.
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
//DTO(Data Transfer Object) : 계층 간 데이터 전송을 위한 객체, 주로 프론트 엔드와 JSON으로 통신하기  위한 객체.
// 주로 프론트엔드와의 통신, 요청과 응답에 사용되는 객체.  일반적으로 이 객체는 주로 프론트엔드와 백엔드 사이에서 데이터를 주고받거나, 서로 다른 계층 간의 데이터 전달을 위해 사용
public class MemberResDto {
    private String email;
    private String pwd;
    private String name;
    private String image;
    private LocalDateTime regDate;

    public static MemberResDto of(Member member) {
        return MemberResDto.builder()
                .name(member.getName())
                .email(member.getEmail())
                .regDate(member.getRegDate())
                .build();
    }
}

