package js.daangnclone.web.attention.dto;

import js.daangnclone.cmn.area.Area;
import js.daangnclone.domain.board.BoardStatus;
import lombok.Builder;
import lombok.Data;

@Data
public class AttentionResponse {

    private String link;
    private String boardTitle;
    private String boardImage;
    private int boardPrice;
    private BoardStatus boardStatus;

    private Area area;

    private long attentionCnt;
    private long chatRoomCnt;

    @Builder
    public AttentionResponse(String link, String boardTitle, String boardImage, int boardPrice, BoardStatus boardStatus, Area area, long attentionCnt, long chatRoomCnt) {
        this.link = link;
        this.boardTitle = boardTitle;
        this.boardImage = boardImage;
        this.boardPrice = boardPrice;
        this.boardStatus = boardStatus;
        this.area = area;
        this.attentionCnt = attentionCnt;
        this.chatRoomCnt = chatRoomCnt;
    }

}
