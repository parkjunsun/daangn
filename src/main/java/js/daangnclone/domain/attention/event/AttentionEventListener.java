package js.daangnclone.domain.attention.event;

import js.daangnclone.cmn.DateUtil;
import js.daangnclone.domain.activity.Activity;
import js.daangnclone.domain.activity.ActivityType;
import js.daangnclone.domain.alarm.Alarm;
import js.daangnclone.domain.alarm.AlarmType;
import js.daangnclone.domain.attention.Attention;
import js.daangnclone.service.activity.ActivityService;
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
    private final ActivityService activityService;

    @EventListener //이벤트리스너 명시
    public void handleAttentionCreatedEvent(AttentionCreatedEvent event) { //EventPublisher 를 통해 이벤트가 발생될 때 전달한 파라미터가 AttentionCreatedEvent일 때 해당 메서드가 호출된다.
        Attention attention = event.getAttention();

        Alarm newAlarm = Alarm.builder()
                .message("회원님이 등록한 " + "\"" + attention.getBoard().getTitle() + "\"" + " 상품에 관심을 가졌습니다.")
                .link("/board/" + attention.getBoard().getId())
                .sender(attention.getMember())
                .receiver(attention.getBoard().getMember())
                .checkedYn("N")
                .clickYn("N")
                .alarmType(AlarmType.ATTENTION_CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        alarmService.load(newAlarm);

        Activity newActivity = Activity.builder()
                .message("님이 등록한 상품에 관심을 가졌습니다.")
                .content(attention.getBoard().getTitle())
                .link("/board/" + attention.getBoard().getId())
                .activityType(ActivityType.ATTENTION_CREATE)
                .sender(attention.getMember())
                .receiver(attention.getBoard().getMember())
                .createdAt(LocalDateTime.now())
                .build();

        activityService.load(newActivity);

    }
}
