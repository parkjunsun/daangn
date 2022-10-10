package js.daangnclone.web.chat.controller;


import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.chat.Chat;
import js.daangnclone.domain.chat.ChatRepository;
import js.daangnclone.domain.member.Member;
import js.daangnclone.service.board.BoardService;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.chat.dto.ChatResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatRepository chatRepository;


    @GetMapping(value = "/board/{boardId}/chat/{roomNum}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> getMsg(@PathVariable Long boardId, @PathVariable String roomNum) {
        return chatRepository.mFindByRoomNum(roomNum)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping("/chat")
    public Mono<Chat> setMsg(@RequestBody Chat chat) { //save 한 데이터가 잘 저장됬나 보고 싶어서 Mono 타입으로 return한것

        log.info("senderId={}", chat.getSenderId());
        log.info("receiverId={}", chat.getReceiverId());
        log.info("msg={}", chat.getMsg());
        chat.setCreatedAt(LocalDateTime.now());
        return chatRepository.save(chat);
    }

}
