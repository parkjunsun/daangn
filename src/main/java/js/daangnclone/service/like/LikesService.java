package js.daangnclone.service.like;

import js.daangnclone.domain.comment.Comment;

public interface LikesService {

    void processLike(Long MemberId, Long commentId);
    String getInpLikeYn(Long memberId, Long commentId);
    long countLikesInComment(Comment comment);
}
