package js.daangnclone.domain.attention.event;

import js.daangnclone.cmn.DateUtil;
import js.daangnclone.domain.alarm.Alarm;
import js.daangnclone.domain.alarm.AlarmType;
import js.daangnclone.domain.attention.Attention;
import js.daangnclone.service.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Async
@Component
@Slf4j
@RequiredArgsConstructor
public class AttentionEventListener {

    private final AlarmService alarmService;

    @EventListener //이벤트리스너 명시
    public void handleAttentionCreatedEvent(AttentionCreatedEvent event) { //EventPublisher 를 통해 이벤트가 발생될 때 전달한 파라미터가 AttentionCreatedEvent일 때 해당 메서드가 호출된다.
        Attention attention = event.getAttention();

        Alarm newAlarm = Alarm.builder()
                .title(attention.getBoard().getTitle())
                .message("등록한 상품에 관심을 가졌습니다.")
                .link("/board/" + attention.getBoard().getId())
                .sender(attention.getMember())
                .receiver(attention.getBoard().getMember())
                .checkedYn("N")
                .alarmType(AlarmType.ATTENTION_CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        alarmService.load(newAlarm);

    }
}
