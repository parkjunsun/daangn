package js.daangnclone.web.purchase.dto;

import js.daangnclone.domain.board.Board;
import lombok.Builder;
import lombok.Data;

@Data
public class PurchaseResponse {

    private Board board;
    private boolean isSentReview;

    @Builder
    public PurchaseResponse(Board board, boolean isSentReview) {
        this.board = board;
        this.isSentReview = isSentReview;
    }
}
