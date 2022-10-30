package js.daangnclone.domain.like.event;

import js.daangnclone.domain.activity.Activity;
import js.daangnclone.domain.activity.ActivityType;
import js.daangnclone.domain.alarm.activityAlarm.ActivityAlarm;
import js.daangnclone.domain.alarm.activityAlarm.ActivityAlarmType;
import js.daangnclone.domain.like.Likes;
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
public class LikesEventListener {

    private final ActivityAlarmService activityAlarmService;
    private final ActivityService activityService;

    @EventListener
    public void handleCommentCreatedEvent(LikesCreatedEvent event) {
        Likes likes = event.getLikes();

        ActivityAlarm newActivityAlarm = ActivityAlarm.builder()
                .message("회원님이 등록한 " + "\"" + likes.getComment().getContent() + "\"" + " 댓글에 좋아요를 눌렀습니다.")
                .link("/board/" + likes.getComment().getBoard().getId())
                .sender(likes.getMember())
                .receiver(likes.getComment().getMember())
                .checkedYn("N")
                .clickYn("N")
                .activityAlarmType(ActivityAlarmType.LIKES_CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        activityAlarmService.load(newActivityAlarm);


        Activity newActivity = Activity.builder()
                .message("님이 등록한 상품에 달린 댓글에 좋아요를 눌렀습니다.")
                .content(likes.getComment().getBoard().getTitle())
                .link("/board/" + likes.getComment().getBoard().getId())
                .activityType(ActivityType.LIKES_CREATE)
                .sender(likes.getMember())
                .receiver(likes.getComment().getBoard().getMember())
                .createdAt(LocalDateTime.now())
                .build();

        activityService.load(newActivity);

    }
}
