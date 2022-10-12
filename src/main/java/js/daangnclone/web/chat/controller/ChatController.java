package js.daangnclone.web.chat.controller;


import js.daangnclone.domain.chat.Chat;
import js.daangnclone.domain.chat.ChatRepository;
import js.daangnclone.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;


    @GetMapping(value = "/board/{boardId}/chat/{roomNum}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> getMsg(@PathVariable Long boardId, @PathVariable String roomNum) {
        return chatService.findChatRoom(roomNum)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping("/chat")
    public Mono<Chat> setMsg(@RequestBody Chat chat) { //save 한 데이터가 잘 저장됬나 보고 싶어서 Mono 타입으로 return한것

        chat.setCreatedAt(LocalDateTime.now());
        return chatService.createChatRoom(chat);
    }

}
