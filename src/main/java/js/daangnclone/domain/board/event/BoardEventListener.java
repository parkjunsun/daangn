package js.daangnclone.domain.board.event;

import js.daangnclone.domain.activity.Activity;
import js.daangnclone.domain.activity.ActivityType;
import js.daangnclone.domain.alarm.Alarm;
import js.daangnclone.domain.alarm.AlarmType;
import js.daangnclone.domain.attention.Attention;
import js.daangnclone.domain.attention.event.AttentionCreatedEvent;
import js.daangnclone.domain.board.Board;
import js.daangnclone.service.activity.ActivityService;
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
public class BoardEventListener {

    private final ActivityService activityService;

    @EventListener //이벤트리스너 명시
    public void handleAttentionCreatedEvent(BoardCreatedEvent event) {
        Board board = event.getBoard();

        Activity newActivity = Activity.builder()
                .message("새로운 상품을 등록하였습니다.")
                .content(board.getTitle())
                .link("/board/" + board.getId())
                .activityType(ActivityType.BOARD_CREATE)
                .sender(board.getMember())
                .receiver(board.getMember())
                .createdAt(LocalDateTime.now())
                .build();

        activityService.load(newActivity);

    }
}
