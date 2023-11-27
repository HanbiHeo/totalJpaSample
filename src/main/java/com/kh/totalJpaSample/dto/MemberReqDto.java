package com.kh.totalJpaSample.dto;

import com.kh.totalJpaSample.constant.Authority;
import com.kh.totalJpaSample.entity.Member;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter @Setter
@AllArgsConstructor // 모든 필드를 파라미터를 받는 생성자
@NoArgsConstructor // 기본생성자
@Builder //빌더 패턴
public class MemberReqDto {
    private String email;
    private String password;
    private String name;
    private String image;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .image(image)
                .authority(Authority.ROLE_USER)
                .build();
    }
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
