package js.daangnclone.web.sale.dto;

import js.daangnclone.cmn.Area;
import js.daangnclone.domain.board.BoardStatus;
import lombok.Builder;
import lombok.Data;

@Data
public class SaleResponse {

    private Long boardId;
    private String boardTitle;
    private String boardImage;
    private int boardPrice;
    private BoardStatus boardStatus;
    private String link;
    private Area area;
    private String diffCreatedAt;

    @Builder
    public SaleResponse(Long boardId, String boardTitle, String boardImage, int boardPrice, BoardStatus boardStatus, String link, Area area, String diffCreatedAt) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardImage = boardImage;
        this.boardPrice = boardPrice;
        this.boardStatus = boardStatus;
        this.link = link;
        this.area = area;
        this.diffCreatedAt = diffCreatedAt;
    }
}
