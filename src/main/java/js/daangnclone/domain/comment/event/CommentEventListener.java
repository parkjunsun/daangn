package js.daangnclone.domain.comment.event;

import js.daangnclone.domain.activity.Activity;
import js.daangnclone.domain.activity.ActivityType;
import js.daangnclone.domain.alarm.activityAlarm.ActivityAlarm;
import js.daangnclone.domain.alarm.activityAlarm.ActivityAlarmType;
import js.daangnclone.domain.comment.Comment;
import js.daangnclone.service.activity.ActivityService;
import js.daangnclone.service.alarm.activityAlarm.ActivityAlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Async
@Component
@RequiredArgsConstructor
public class CommentEventListener {

    private final ActivityAlarmService activityAlarmService;
    private final ActivityService activityService;

    @EventListener
    public void handleCommentCreatedEvent(CommentCreatedEvent event) {
        Comment comment = event.getComment();

        ActivityAlarm newActivityAlarm = ActivityAlarm.builder()
                .message("회원님이 등록한 " + "\"" + comment.getBoard().getTitle() + "\"" + " 상품에 댓글을 달았습니다.")
                .link("/board/" + comment.getBoard().getId())
                .sender(comment.getMember())
                .receiver(comment.getBoard().getMember())
                .checkedYn("N")
                .clickYn("N")
                .activityAlarmType(ActivityAlarmType.COMMENT_CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        activityAlarmService.load(newActivityAlarm);

        Activity newActivity = Activity.builder()
                .message("등록한 상품에 댓글을 등록했습니다.")
                .content(comment.getBoard().getTitle())
                .link("/board/" + comment.getBoard().getId())
                .activityType(ActivityType.COMMENT_CREATE)
                .sender(comment.getMember())
                .receiver(comment.getBoard().getMember())
                .createdAt(LocalDateTime.now())
                .build();

        activityService.load(newActivity);

    }
}
