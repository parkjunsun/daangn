package js.daangnclone.domain.attention.event;

import js.daangnclone.domain.attention.Attention;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Async
@Transactional(readOnly = true)
@Component
@Slf4j
public class AttentionEventListener {

    @EventListener //이벤트리스너 명시
    public void handleAttentionCreatedEvent(AttentionCreatedEvent event) { //EventPublisher 를 통해 이벤트가 발생될 때 전달한 파라미터가 AttentionCreatedEvent일 때 해당 메서드가 호출된다.
        Attention attention = event.getAttention();
        log.info("attention등록 게시글ID={}", attention.getBoard().getTitle());
        log.info("attention등록 유저ID={}", attention.getMember().getUsername());
    }
}
