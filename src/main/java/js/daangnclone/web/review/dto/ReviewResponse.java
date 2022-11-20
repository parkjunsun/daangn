package js.daangnclone.web.review.dto;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import lombok.Builder;
import lombok.Data;

@Data
public class ReviewResponse {

    private Long id;
    private String senderName;
    private String boardTitle;
    private String boardImage;

    @Builder
    public ReviewResponse(Long id, String senderName, String boardTitle, String boardImage) {
        this.id = id;
        this.senderName = senderName;
        this.boardTitle = boardTitle;
        this.boardImage = boardImage;
    }

}
