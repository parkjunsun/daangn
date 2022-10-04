package js.daangnclone.domain.like.event;

import js.daangnclone.domain.like.Likes;
import lombok.Getter;

/**
 * 좋아요 생성시 발생시킬 이벤트
 */
@Getter
public class LikesCreatedEvent {

    private final Likes likes;

    public LikesCreatedEvent(Likes likes) {
        this.likes = likes;
    }
}
