package js.daangnclone.domain.attention.event;

import js.daangnclone.domain.attention.Attention;
import lombok.Getter;


/**
 * 관심 생성시 발생시킬 이벤트
 */

@Getter
public class AttentionCreatedEvent {

    private final Attention attention;

    public AttentionCreatedEvent(Attention attention) {
        this.attention = attention;
    }
}
