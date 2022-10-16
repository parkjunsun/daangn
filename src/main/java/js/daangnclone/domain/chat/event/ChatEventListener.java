package js.daangnclone.domain.chat.event;

import js.daangnclone.domain.alarm.Alarm;
import js.daangnclone.domain.alarm.AlarmType;
import js.daangnclone.domain.chat.Chat;
import js.daangnclone.service.alarm.AlarmService;
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

    @EventListener //이벤트리스너 명시
    public void handleChatCreatedEvent(ChatCreatedEvent event) { //EventPublisher 를 통해 이벤트가 발생될 때 전달한 파라미터가 AttentionCreatedEvent일 때 해당 메서드가 호출된다.
        Chat chat = event.getChat();

        Alarm newAlarm = Alarm.builder()
                .message("회원님이 등록한 " + "\"" + chat.getBoard().getTitle() + "\"" + " 상품에 대화 요청을 했습니다.")
                .link("/board/" + chat.getBoard().getId() + "/chat?roomNum=" + chat.getRoomNum())
                .sender(chat.getBuyer())
                .receiver(chat.getSeller())
                .checkedYn("N")
                .clickYn("N")
                .alarmType(AlarmType.CHAT_CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        alarmService.load(newAlarm);

    }

}
