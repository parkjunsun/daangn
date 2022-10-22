package js.daangnclone.web.chat.dto;

import js.daangnclone.domain.board.BoardStatus;
import lombok.Builder;
import lombok.Data;

@Data
public class ChatResponse {

    private Long senderId;
    private String senderName;

    private Long boardId;
    private String boardTitle;
    private String boardImage;
    private int boardPrice;
    private BoardStatus boardStatus;

    private Long receiverId;
    private String receiverName;

    private String roomNum;

    @Builder
    public ChatResponse(Long senderId, String senderName, Long boardId, String boardTitle, String boardImage, int boardPrice, BoardStatus boardStatus, Long receiverId, String receiverName, String roomNum) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardImage = boardImage;
        this.boardPrice = boardPrice;
        this.boardStatus = boardStatus;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.roomNum = roomNum;
    }
}
