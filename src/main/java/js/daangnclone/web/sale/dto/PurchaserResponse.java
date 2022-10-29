package js.daangnclone.web.sale.dto;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import lombok.Builder;
import lombok.Data;

@Data
public class PurchaserResponse {

    private Member sender;
    private Board board;
    private String roomNum;
    private String diffLastCommentUpdatedAt;

    @Builder
    public PurchaserResponse(Member sender, Board board, String roomNum, String diffLastCommentUpdatedAt) {
        this.sender = sender;
        this.board = board;
        this.roomNum = roomNum;
        this.diffLastCommentUpdatedAt = diffLastCommentUpdatedAt;
    }

}
