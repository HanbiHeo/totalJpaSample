package com.kh.totalJpaSample.service;
import com.kh.totalJpaSample.dto.MemberDto;
import com.kh.totalJpaSample.entity.Member;
import com.kh.totalJpaSample.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//Service : 해당 객체를 빈으로 등록하기. 컨트롤러로 가져다 쓸 때 빈으로 등록했기 때문에 언제든 가져다 쓸 수 있음.
@Service
//RequiredArgsConstructor : 생성자로 의존성 주입을 함. 매개변수가 전부 포함된 생성자를 자동으로 생성해준다
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository; // 객체 생성을 안한 의존성 주입. 외부에서 만든걸 가져다 쓰기 때문에 의존성 주입 받아야함. 생성자 따로 안만들어도 된다. new를 안해주고 가져다 씀.
    //회원 등록
    public boolean saveMember(MemberDto memberDto) {
        //이미 등록되어있는 회원인지 확인하는 쿼리문
        boolean isReg = memberRepository.existsByEmail(memberDto.getEmail());
        if(isReg) return false;

        Member member = new Member();
        member.setEmail(memberDto.getEmail());
        member.setPassword(memberDto.getPassword());
        member.setName(memberDto.getName());
        memberRepository.save(member);
        return true;
    }

    //회원 전체 조회
    public List<MemberDto> getMemberList() {
        List<MemberDto> memberDtos = new ArrayList<>();
        List<Member> members = memberRepository.findAll(); // select *과 같음 = findAll()
        //향상된 for문
        for(Member member  : members) {
            memberDtos.add(convertEntityToDto(member));
        }
        return memberDtos;
    }

    //회원 상세 조회
    public MemberDto getMemberDetail(String email) {
            Member member = memberRepository.findByEmail(email).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
            );
            return convertEntityToDto(member);
    }


    //변환객체 만들기 ; 회원 엔티티를 DTO로 변환하는 메소드 함수 만들기
    private MemberDto convertEntityToDto(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail(member.getEmail());
        memberDto.setPassword(member.getPassword());
        memberDto.setName(member.getName());
        memberDto.setRegDate(member.getRegDate());
        return memberDto;
    }
}
