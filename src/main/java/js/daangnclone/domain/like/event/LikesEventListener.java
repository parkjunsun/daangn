package js.daangnclone.domain.like.event;

import js.daangnclone.domain.alarm.Alarm;
import js.daangnclone.domain.alarm.AlarmType;
import js.daangnclone.domain.comment.Comment;
import js.daangnclone.domain.comment.event.CommentCreatedEvent;
import js.daangnclone.domain.like.Likes;
import js.daangnclone.service.alarm.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Async
@Component
@RequiredArgsConstructor
public class LikesEventListener {

    private final AlarmService alarmService;

    @EventListener
    public void handleCommentCreatedEvent(LikesCreatedEvent event) {
        Likes likes = event.getLikes();

        Alarm newAlarm = Alarm.builder()
                .message("회원님이 등록한 " + "\"" + likes.getComment().getContent() + "\"" + " 댓글에 좋아요를 눌렀습니다.")
                .link("/board/" + likes.getComment().getBoard().getId())
                .sender(likes.getMember())
                .receiver(likes.getComment().getMember())
                .checkedYn("N")
                .clickYn("N")
                .alarmType(AlarmType.LIKES_CREATED)
                .createdAt(LocalDateTime.now())
                .build();

        alarmService.load(newAlarm);

    }
}
