package js.daangnclone.domain.chat.event;

import js.daangnclone.domain.chat.Message;
import lombok.Getter;

/**
 * 채팅 메세지 생성시 발생시킬 이벤트
 */

@Getter
public class MessageCreatedEvent {

    private final Message message;

    public MessageCreatedEvent(Message message) {
        this.message = message;
    }
}
