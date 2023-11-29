package com.kh.totalJpaSample.controller;
import com.kh.totalJpaSample.dto.MemberReqDto;
import com.kh.totalJpaSample.dto.MemberResDto;
import com.kh.totalJpaSample.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//import static com.kh.totalJpaSample.utils.Common.CORS_ORIGIN; //CORS(Cross-Origin Resource Sharing) 정책을 설정하여 다른 도메인에서의 요청도 허용하도록 설정합니다.
//
////Slf4j : log 4j라고 함. 로깅을 위한 어노테이션, 로그 기록을 위해 사용  grep을 깔았었는데, systemout.println 대신하여 로그를 남겨서 기록하거나 화면에 출력할 때 쓴다. 로그를 필수적으로 남기면 좋음.
//@Slf4j
//@CrossOrigin(origins = CORS_ORIGIN)  // CORS(Cross-Origin Resource Sharing) 정책 설정
//@RestController // RESTful API를 처리하는 컨트롤러임을 나타내는 어노테이션
//@RequestMapping("/users" )// API의 기본 경로 지정. 접근경로 지정
//@RequiredArgsConstructor  // 의존성 주입을 위한 생성자 자동 생성. Argument가 있는 생성자를 만들겠다.
//public class MemberController {
//    //memberService 만든거 땡겨오기. 의존성 주입
//    private final MemberService memberService;
//    //회원 등록 메서드
//    @PostMapping("/new")
//    //memberRegister sublet dispetch 통해서 불려오기 때문에 이름은 아무거나 상관 없음 ResponseBody 데이터가 body 에 담겨서 전달(프론트에서 날린 데이터를 받아와야함)
//    public ResponseEntity<Boolean> memberRegister(@RequestBody MemberResDto memberDto) {
//        log.info("email : " , memberDto.getEmail());
//        boolean isTrue = memberService.saveMember(memberDto);  // MemberService 를 이용하여 회원 등록 처리
//        return ResponseEntity.ok(isTrue);  // 결과 반환
//    }
//    //회원 전체 목록 조회 메서드
//    @GetMapping("/list")
//    public ResponseEntity<List<MemberResDto>> memberList() {
//        List<MemberResDto> list = memberService.getMemberList();   // MemberService 를 이용하여 전체 회원 목록 조회
//        return ResponseEntity.ok(list);
//    }
//
//    //회원 상세조회 메서드
//    @GetMapping("/detail/{email}")
//    public ResponseEntity<MemberResDto> memberDetail(@PathVariable String email) {
//        // MemberService 를 이용하여 특정 이메일을 가진 회원의 상세 정보 조회
//        MemberResDto memberDto = memberService.getMemberDetail(email);
//        return ResponseEntity.ok(memberDto);
//    }
//    // 로그인
//    @PostMapping("/login")
//    public ResponseEntity<Boolean> memberLogin(@RequestBody MemberResDto memberDto) {
//        boolean isTrue = memberService.login(memberDto.getEmail(), memberDto.getPwd());
//        return ResponseEntity.ok(isTrue);
//    }
//    // 회원 존재 여부 확인
//    @GetMapping("/check")
//    public ResponseEntity<Boolean> isMember(@RequestParam String email) {
//        log.info("email: {}", email);
//        boolean isReg = memberService.isMember(email);
//        return ResponseEntity.ok(!isReg);
//    }
//    // 회원 삭제
//    @DeleteMapping("/del/{email}")
//    public ResponseEntity<Boolean> memberDelete(@PathVariable String email) {
//        boolean isTrue = memberService.deleteMember(email);
//        return ResponseEntity.ok(isTrue);
//    }
//    // ---------------------페이지 네이션-----------------
//    //페이지 네이션 조회
//    @GetMapping("/list/page")
//    public ResponseEntity<List<MemberResDto>> memberList(@RequestParam(defaultValue = "0") int page,
//                                                         @RequestParam(defaultValue = "10") int size) {
//        List<MemberResDto> list = memberService.getMemberList(page, size);
//        return ResponseEntity.ok(list);
//    }

//    //총 페이지 수 조회(총 페이지 수 만들기)
//    @GetMapping("/list/page-cnt")
//    public ResponseEntity<Integer> memberPageCount(@RequestParam(defaultValue = "0") int page,
//                                                                                                                           @RequestParam(defaultValue = "5") int size) {
//        int pageCnt = memberService.getMemberPage(page, size);
//        return ResponseEntity.ok(pageCnt);
//    }

//}

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    // 회원 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<MemberResDto>> memberList() {
        List<MemberResDto> list = memberService.getMemberList();
        return ResponseEntity.ok(list);
    }
    // 총 페이지 수
    @GetMapping("/list/count")
    public ResponseEntity<Integer> memberCount(@RequestParam(defaultValue = "20") int page,
                                               @RequestParam(defaultValue = "0") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        int pageCnt = memberService.getMemberPage(pageRequest);
        return ResponseEntity.ok(pageCnt);
    }

    // 회원 조회 페이지네이션
    @GetMapping("/list/page")
    public ResponseEntity<List<MemberResDto>> memberList(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "20") int size) {
        List<MemberResDto> list = memberService.getMemberList(page, size);
        return ResponseEntity.ok(list);
    }
    // 회원 상세 조회
    @GetMapping("/detail/{email}")
    public ResponseEntity<MemberResDto> memberDetail(@PathVariable String email) {
        MemberResDto memberDto = memberService.getMemberDetail(email);
        return ResponseEntity.ok(memberDto);
    }
    // 회원 수정
    @PutMapping("/modify")
    public ResponseEntity<Boolean> memberModify(@RequestBody MemberReqDto memberDto) {
        log.info("memberDto: {}", memberDto.getEmail());
        boolean isTrue = memberService.modifyMember(memberDto);
        return ResponseEntity.ok(isTrue);
    }
    // 회원 삭제
    @DeleteMapping("/del/{email}")
    public ResponseEntity<Boolean> memberDelete(@PathVariable String email) {
        boolean isTrue = memberService.deleteMember(email);
        return ResponseEntity.ok(isTrue);
    }
}