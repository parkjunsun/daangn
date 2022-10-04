package js.daangnclone.domain.comment.event;

import js.daangnclone.domain.alarm.Alarm;
import js.daangnclone.domain.alarm.AlarmType;
import js.daangnclone.domain.comment.Comment;
import js.daangnclone.service.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Async
@Component
@RequiredArgsConstructor
public class CommentEventListener {

    private final AlarmService alarmService;

    @EventListener
    public void handleCommentCreatedEvent(CommentCreatedEvent event) {
        Comment comment = event.getComment();

        Alarm newAlarm = Alarm.builder()
                .title(comment.getBoard().getTitle())
                .message("등록한 상품에 댓글을 달았습니다.")
                .link("/board/" + comment.getBoard().getId())
                .sender(comment.getMember())
                .receiver(comment.getBoard().getMember())
                .checkedYn("N")
                .alarmType(AlarmType.COMMENT_CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        alarmService.load(newAlarm);

    }
}
