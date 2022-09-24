package js.daangnclone.service.like;

public interface LikesService {

    void processLike(Long MemberId, Long commentId);
    String getInpLikeYn(Long memberId, Long commentId);
}
