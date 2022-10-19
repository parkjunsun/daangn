package js.daangnclone.web.chat.controller;


import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.chat.Chat;
import js.daangnclone.domain.chat.Message;
import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.board.BoardService;
import js.daangnclone.service.chat.ChatService;
import js.daangnclone.service.chat.MessageService;
import js.daangnclone.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final MessageService messageService;
    private final MemberService memberService;
    private final BoardService boardService;
    private final ChatService chatService;


    @GetMapping(value = "/board/{boardId}/chat/{roomNum}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Message> getMsg(@PathVariable Long boardId, @PathVariable String roomNum) {

        return messageService.findChatRoom(roomNum)
                .subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping("/chat")
    public Mono<Message> setMsg(@RequestBody Message message, @AuthenticationPrincipal PrincipalUserDetails principalUserDetails) { //save 한 데이터가 잘 저장됬나 보고 싶어서 Mono 타입으로 return한것

        Long memberId = principalUserDetails.getMember().getId();
        Member findMember = memberService.findMember(memberId);
        Board findBoard = boardService.findBoard(message.getBoardId());

        Optional<Chat> findChatRoom = chatService.findChatRoom(message.getRoomNum());


        //채팅메세지를 한번도 보내지 않아 채팅방이 생성되있지 않은 경우에는
        //채팅방을 만들고 메세지 sender를 상품 구매자, 메세지 receiver를 상품 판매자로 간주한다.
        if (findChatRoom.isEmpty()) {
            Chat chatRoom = Chat.builder()
                    .roomNum(message.getRoomNum())
                    .board(findBoard)
                    .seller(findBoard.getMember())
                    .buyer(findMember)
                    .lastComment(message.getMsg())
                    .build();
            chatService.createChatRoom(chatRoom);
        } else {
            Chat chatRoom = findChatRoom.get();
            chatService.updateLastComment(chatRoom, message.getMsg());
        }
       message.setCreatedAt(LocalDateTime.now());
        return messageService.createMessage(message);
    }

}
