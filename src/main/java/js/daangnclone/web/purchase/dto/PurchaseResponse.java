package js.daangnclone.web.purchase.dto;

import js.daangnclone.domain.board.Board;
import lombok.Builder;
import lombok.Data;

@Data
public class PurchaseResponse {

    private Board board;

    @Builder
    public PurchaseResponse(Board board) {
        this.board = board;
    }
}
