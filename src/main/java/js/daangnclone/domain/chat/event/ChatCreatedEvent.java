package js.daangnclone.domain.chat.event;


import js.daangnclone.domain.chat.Message;
import lombok.Getter;

/**
 * 채팅 생성시 발생시킬 이벤트
 */

@Getter
public class ChatCreatedEvent {

    private final Message chat;

    public ChatCreatedEvent(Message chat) {
        this.chat = chat;
    }
}
