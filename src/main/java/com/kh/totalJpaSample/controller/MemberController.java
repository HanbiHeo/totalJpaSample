package com.kh.totalJpaSample.controller;
import com.kh.totalJpaSample.dto.MemberDto;
import com.kh.totalJpaSample.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kh.totalJpaSample.utils.Common.CORS_ORIGIN; //CORS(Cross-Origin Resource Sharing) 정책을 설정하여 다른 도메인에서의 요청도 허용하도록 설정합니다.

//Slf4j : log 4j라고 함. 로깅을 위한 어노테이션, 로그 기록을 위해 사용  grep을 깔았었는데, systemout.println 대신하여 로그를 남겨서 기록하거나 화면에 출력할 때 쓴다. 로그를 필수적으로 남기면 좋음.
@Slf4j
@CrossOrigin(origins = CORS_ORIGIN)  // CORS(Cross-Origin Resource Sharing) 정책 설정
@RestController // RESTful API를 처리하는 컨트롤러임을 나타내는 어노테이션
@RequestMapping("/users" )// API의 기본 경로 지정. 접근경로 지정
@RequiredArgsConstructor  // 의존성 주입을 위한 생성자 자동 생성. Argument가 있는 생성자를 만들겠다.
public class MemberController {
    //memberService 만든거 땡겨오기. 의존성 주입
    private final MemberService memberService;
    //회원 등록 메서드
    @PostMapping("/new")
    //memberRegister sublet dispetch 통해서 불려오기 때문에 이름은 아무거나 상관 없음 ResponseBody 데이터가 body 에 담겨서 전달(프론트에서 날린 데이터를 받아와야함)
    public ResponseEntity<Boolean> memberRegister(@RequestBody  MemberDto memberDto) {
        log.info("email : " , memberDto.getEmail());
        boolean isTrue = memberService.saveMember(memberDto);  // MemberService 를 이용하여 회원 등록 처리
        return ResponseEntity.ok(isTrue);  // 결과 반환
    }
    //회원 전체 목록 조회 메서드
    @GetMapping("/list")
    public ResponseEntity<List<MemberDto>> memberList() {
        List<MemberDto> list = memberService.getMemberList();   // MemberService 를 이용하여 전체 회원 목록 조회
        return ResponseEntity.ok(list);
    }

    //회원 상세조회 메서드
    @GetMapping("/detail/{email}")
    public ResponseEntity<MemberDto> memberDetail(@PathVariable String email) {
        // MemberService 를 이용하여 특정 이메일을 가진 회원의 상세 정보 조회
        MemberDto memberDto = memberService.getMemberDetail(email);
        return ResponseEntity.ok(memberDto);
    }

    // ---------------------페이지 네이션-----------------
    //페이지 네이션 조회
    @GetMapping("/list/page")
    public ResponseEntity<List<MemberDto>> memberList(@RequestParam(defaultValue = "0") int page,
                                                                                                                                  @RequestParam(defaultValue = "10") int size) {
        List<MemberDto> list = memberService.getMemberList(page, size);
        return ResponseEntity.ok(list);
    }

    //총 페이지 수 조회(총 페이지 수 만들기)
    @GetMapping("/list/page-cnt")
    public ResponseEntity<Integer> memberPageCount(@RequestParam(defaultValue = "0") int page,
                                                                                                                           @RequestParam(defaultValue = "5") int size) {
        int pageCnt = memberService.getMemberPage(page, size);
        return ResponseEntity.ok(pageCnt);
    }

}
