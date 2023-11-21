package com.kh.totalJpaSample.service;
import com.kh.totalJpaSample.dto.MemberDto;
import com.kh.totalJpaSample.entity.Member;
import com.kh.totalJpaSample.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//Service : 해당 객체를 빈으로 등록하기. 컨트롤러로 가져다 쓸 때 빈으로 등록했기 때문에 언제든 가져다 쓸 수 있음.
@Service
//RequiredArgsConstructor : 생성자로 의존성 주입을 함. 매개변수가 전부 포함된 생성자를 자동으로 생성해준다
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository; // 객체 생성을 안한 의존성 주입(MemberRepository를 주입받음). 외부에서 만든걸 가져다 쓰기 때문에 의존성 주입 받아야함. 생성자 따로 안만들어도 된다. new를 안해주고 가져다 씀.
    //회원 등록 메서드
    public boolean saveMember(MemberDto memberDto) {
        boolean isReg = memberRepository.existsByEmail(memberDto.getEmail());   //이미 등록되어있는 회원인지 확인하는 쿼리문
        if(isReg) return false; // 이미 등록된 회원이면 등록하지 않음

        Member member = new Member(); // Member 엔티티 생성
        member.setEmail(memberDto.getEmail());
        member.setPassword(memberDto.getPassword());
        member.setName(memberDto.getName());
        memberRepository.save(member); // 회원 저장
        return true; // 회원 등록 성공
    }

    //회원 전체 조회
    public List<MemberDto> getMemberList() {
        List<MemberDto> memberDtos = new ArrayList<>();
        List<Member> members = memberRepository.findAll(); // select *과 같음 = findAll()  _  모든 회원 조회
        //향상된 for문
        for(Member member  : members) {  // 회원 엔티티를 DTO로 변환하여 리스트에 추가
            memberDtos.add(convertEntityToDto(member));
        }
        return memberDtos; // 회원 목록 반환
    }

//    -------------페이지 네이션 사용법----------------------
    //페이지네이션 조회 (오버로딩)
    public List<MemberDto> getMemberList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<MemberDto> memberDtos = new ArrayList<>();
        List<Member> members = memberRepository.findAll(pageable).getContent(); // 지정한 페이지 만큼만 가져옴.
        for(Member member : members) {
            memberDtos.add(convertEntityToDto(member));
        }
        return memberDtos;
    }
    //페이지 수 조회 getMemberPage 만들기
    public int getMemberPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return memberRepository.findAll(pageable).getTotalPages();
    }



    //회원 상세 조회
    public MemberDto getMemberDetail(String email) {
            Member member = memberRepository.findByEmail(email).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
            );
            return convertEntityToDto(member);  // 회원 엔티티를 DTO로 변환하여 반환
    }



    //변환객체 만들기 ; 회원 엔티티를 DTO로 변환하는 메소드 함수 만들기
    private MemberDto convertEntityToDto(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setEmail(member.getEmail());
        memberDto.setPassword(member.getPassword());
        memberDto.setName(member.getName());
        memberDto.setRegDate(member.getRegDate());
        return memberDto; // 회원 DTO 반환
    }
}
