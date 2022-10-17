package js.daangnclone.domain.chat.event;

import js.daangnclone.domain.chat.Message;
import js.daangnclone.domain.chatNotification.ChatNotification;
import js.daangnclone.service.board.BoardService;
import js.daangnclone.service.chatNotification.ChatNotificationService;
import js.daangnclone.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Async
@Component
@RequiredArgsConstructor
public class MessageEventListener {

    private final ChatNotificationService chatNotificationService;
    private final MemberService memberService;
    private final BoardService boardService;

    @EventListener
    public void handleMessageCreatedEvent(MessageCreatedEvent event) {
        Message message = event.getMessage();

        ChatNotification newChatNotification = ChatNotification.builder()
                .sender(memberService.findMember(message.getSenderId()))
                .receiver(memberService.findMember(message.getReceiverId()))
                .board(boardService.findBoard(message.getBoardId()))
                .checkedYn("N")
                .clickYn("N")
                .createdAt(LocalDateTime.now())
                .build();

        chatNotificationService.load(newChatNotification);

    }

}
