package js.daangnclone.domain.review.event;

import js.daangnclone.domain.activity.Activity;
import js.daangnclone.domain.activity.ActivityType;
import js.daangnclone.domain.alarm.activityAlarm.ActivityAlarm;
import js.daangnclone.domain.alarm.activityAlarm.ActivityAlarmType;
import js.daangnclone.domain.review.Review;
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
public class ReviewEventListener {

    private final ActivityAlarmService activityAlarmService;
    private final ActivityService activityService;

    @EventListener
    public void handleReviewCreatedEvent(ReviewCreatedEvent event) {
        Review review = event.getReview();

        ActivityAlarm newActivityAlarm = ActivityAlarm.builder()
                .message("회원님과 거래한 " + "\"" + review.getBoard().getTitle() + "\"" + " 상품에 따뜻한 후기를 보냈습니다.")
                .link("/reviewList/" + review.getId())
                .sender(review.getSender())
                .receiver(review.getReceiver())
                .checkedYn("N")
                .clickYn("N")
                .activityAlarmType(ActivityAlarmType.REVIEW_CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        activityAlarmService.load(newActivityAlarm);

        Activity newActivity = Activity.builder()
                .message("후기를 보냈습니다.")
                .content(review.getBoard().getTitle())
                .link("/reviewList/" + review.getId())
                .activityType(ActivityType.REVIEW_CREATE)
                .sender(review.getSender())
                .receiver(review.getReceiver())
                .createdAt(LocalDateTime.now())
                .build();

        activityService.load(newActivity);
    }

}
