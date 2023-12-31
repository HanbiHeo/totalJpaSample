package com.kh.totalJpaSample.dto;
import com.kh.totalJpaSample.service.ChatService;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.WebSocketSession;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Slf4j
public class ChatRoomResDto {
    private String roomId; // 채팅방 ID
    private String name; //채팅방 이름
    private LocalDateTime regDate; // 채팅방 생성 시간

    @JsonIgnore // WebSocketSession 직렬화 방지
    private Set<WebSocketSession> sessions; // 채팅방에 입장한 세션 정보담을 Set

    //세션 수가 0인지 확인하는 메서드
    public boolean isSessionEmpty() {
        return this.sessions.size() == 0;
    }

    @Builder // 빌더 패턴 적용
    public ChatRoomResDto(String roomId, String name, LocalDateTime regDate) {
        this.roomId = roomId;
        this.name = name;
        this.regDate = regDate;
        this.sessions = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }
}