package js.daangnclone.web.like.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class LikesResponse {

    private Long id;
    private Long memberId;
    private Long commentId;

    @Builder
    public LikesResponse(Long id, Long memberId, Long commentId) {
        this.id = id;
        this.memberId = memberId;
        this.commentId = commentId;
    }
}
