package js.daangnclone.domain.chat.event;

import js.daangnclone.domain.activity.Activity;
import js.daangnclone.domain.activity.ActivityType;
import js.daangnclone.domain.chat.Chat;
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
public class ChatEventListener {

    private final ActivityService activityService;

    @EventListener //이벤트리스너 명시
    public void handleAttentionCreatedEvent(ChatCreatedEvent event) {
        Chat chat = event.getChat();

        Activity newActivity = Activity.builder()
                .message("님이 등록한 상품에 채팅을 오픈했습니다.")
                .content(chat.getBoard().getTitle())
                .link("/board/" + chat.getBoard().getId())
                .activityType(ActivityType.CHAT_CREATE)
                .sender(chat.getBuyer())
                .receiver(chat.getBoard().getMember())
                .createdAt(LocalDateTime.now())
                .build();

        activityService.load(newActivity);

    }
}
