package com.kh.totalJpaSample.controller;
import com.kh.totalJpaSample.dto.MemberDto;
import com.kh.totalJpaSample.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kh.totalJpaSample.utils.Common.CORS_ORIGIN;

//Slf4j : log 4j라고 함. grep을 깔았었는데, systemout.print.ln 대신하여 로그를 남겨서 기록하거나 화면에 출력할 때 쓴다. 로그를 필수적으로 남기면 좋음.
@Slf4j
@CrossOrigin(origins = CORS_ORIGIN)
@RestController
//접근경로 지정
@RequestMapping("/users")
//Argument가 있는 생성자를 만들겠다.
@RequiredArgsConstructor
public class MemberController {
    //memberService만든거 땡겨오기. 의존성 주입
    private final MemberService memberService;
    //회원 등록
    @PostMapping("/new")
    //memberRegister sublet dispetch통해서 불려오기 때문에 이름은 아무거나 상관 없음 ResponseBody데이터가 body에 담겨서 전달(프론트에서 날린 데이터를 받아와야함)
    public ResponseEntity<Boolean> memberRegister(@RequestBody  MemberDto memberDto) {
        log.info("email : " , memberDto.getEmail());
        boolean isTrue = memberService.saveMember(memberDto);
        return ResponseEntity.ok(isTrue);
    }
    //회원 전체 조회
    @GetMapping("/list")
    public ResponseEntity<List<MemberDto>> memberList() {
        List<MemberDto> list = memberService.getMemberList();
        return ResponseEntity.ok(list);
    }

    //회원 상세조회
    @GetMapping("/detail/{email}")
    public ResponseEntity<MemberDto> memberDetail(@PathVariable String email) {
        MemberDto memberDto = memberService.getMemberDetail(email);
        return ResponseEntity.ok(memberDto);

    }

}
