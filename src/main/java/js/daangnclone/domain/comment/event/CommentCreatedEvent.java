package js.daangnclone.domain.comment.event;

import js.daangnclone.domain.comment.Comment;
import lombok.Getter;

/**
 * 댓글 생성시 발생시킬 이벤트
 */
@Getter
public class CommentCreatedEvent {

    private final Comment comment;

    public CommentCreatedEvent(Comment comment) {
        this.comment = comment;
    }
}
