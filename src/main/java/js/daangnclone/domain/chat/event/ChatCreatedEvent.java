package js.daangnclone.domain.chat.event;

import js.daangnclone.domain.chat.Chat;
import lombok.Getter;

/**
 * 채팅 생성시 발생시킬 이벤트
 */

@Getter
public class ChatCreatedEvent {

    private final Chat chat;

    public ChatCreatedEvent(Chat chat) {
        this.chat = chat;
    }
}
