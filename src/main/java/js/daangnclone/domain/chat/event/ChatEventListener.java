package js.daangnclone.domain.chat.event;

import js.daangnclone.domain.alarm.Alarm;
import js.daangnclone.domain.alarm.AlarmType;
import js.daangnclone.domain.attention.Attention;
import js.daangnclone.domain.attention.event.AttentionCreatedEvent;
import js.daangnclone.domain.chat.Chat;
import js.daangnclone.service.alarm.AlarmService;
import js.daangnclone.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Async
@Component
@Slf4j
@RequiredArgsConstructor
public class ChatEventListener {

    private final AlarmService alarmService;
    private final MemberService memberService;

    @EventListener //이벤트리스너 명시
    public void handleChatCreatedEvent(ChatCreatedEvent event) { //EventPublisher 를 통해 이벤트가 발생될 때 전달한 파라미터가 AttentionCreatedEvent일 때 해당 메서드가 호출된다.
        Chat chat = event.getChat();

        Alarm newAlarm = Alarm.builder()
                .message("회원님이 등록한 " + "\"" + chat.getBoardTitle() + "\"" + " 상품에 대화 요청을 했습니다.")
                .link("/board/" + chat.getBoardId() + "/chat?roomNum=" + chat.getRoomNum())
                .sender(memberService.findMember(chat.getSenderId()))
                .receiver(memberService.findMember(chat.getReceiverId()))
                .checkedYn("N")
                .clickYn("N")
                .alarmType(AlarmType.ATTENTION_CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        alarmService.load(newAlarm);

    }

}
