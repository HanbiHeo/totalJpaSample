package com.kh.totalJpaSample.service;

import com.kh.totalJpaSample.dto.MemberReqDto;
import com.kh.totalJpaSample.dto.MemberResDto;
import com.kh.totalJpaSample.entity.Member;
import com.kh.totalJpaSample.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 회원 가입 여부 확인
    public boolean isMember(String email) {
        return memberRepository.existsByEmail(email);
    }

    // 회원 상세 조회
    public MemberResDto getMemberDetail(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
        );
        return convertEntityToDto(member);
    }
    // 회원 가입
//    public boolean saveMember(MemberReqDto memberDto) {
//        Member member = new Member();
//        member.setEmail(memberDto.getEmail());
//        member.setName(memberDto.getName());
//        member.setPassword(memberDto.getPassword());
//        member.setImage(memberDto.getImage());
//        memberRepository.save(member);
//        return true;
//    }
    // 회원 수정
    public boolean modifyMember(MemberReqDto memberDto) {
        try {
            Member member = memberRepository.findByEmail(memberDto.getEmail()).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
            );
            member.setName(memberDto.getName());
            member.setImage(memberDto.getImage());
            memberRepository.save(member);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 로그인
//    public boolean login(String email, String pwd) {
//        log.info("email: {}, pwd: {}", email, pwd);
//        Optional<Member> member = memberRepository.findByEmailAndPassword(email, pwd);
//        log.info("member: {}", member);
//        return member.isPresent();
//    }
    // 회원 삭제
    public boolean deleteMember(String email) {
        try {
            Member member = memberRepository.findByEmail(email).orElseThrow(
                    () -> new RuntimeException("해당 회원이 존재하지 않습니다.")
            );
            memberRepository.delete(member);
            return true; // 회원이 존재하면 true 반환
        } catch (RuntimeException e) {
            return false; // 회원이 존재하지 않으면 false 반환
        }
    }

    // 회원 전체 조회
    public List<MemberResDto> getMemberList() {
        List<Member> members = memberRepository.findAll();
        List<MemberResDto> memberDtos = new ArrayList<>();
        for(Member member : members) {
            memberDtos.add(convertEntityToDto(member));
        }
        return memberDtos;
    }
    // 총 페이지 수
    public int getMemberPage(Pageable pageable) {
        return memberRepository.findAll(pageable).getTotalPages();
    }

    // 회원 조회 : 페이지 네이션
    public List<MemberResDto> getMemberList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Member> members = memberRepository.findAll(pageable).getContent();
        List<MemberResDto> memberDtos = new ArrayList<>();
        for(Member member : members) {
            memberDtos.add(convertEntityToDto(member));
        }
        return memberDtos;
    }

    // 회원 엔티티를 회원 DTO로 변환
    private MemberResDto convertEntityToDto(Member member) {
        MemberResDto memberDto = new MemberResDto();
        memberDto.setEmail(member.getEmail());
        memberDto.setName(member.getName());
        memberDto.setImage(member.getImage());
        memberDto.setRegDate(member.getRegDate());
        return memberDto;
    }
}