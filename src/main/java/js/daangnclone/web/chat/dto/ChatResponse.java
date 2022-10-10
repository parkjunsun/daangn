package js.daangnclone.web.chat.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ChatResponse {

    private Long senderId;
    private String senderName;

    private Long boardId;
    private String boardTitle;

    private Long receiverId;
    private String receiverName;

    private String roomNum;

    @Builder
    public ChatResponse(Long senderId, String senderName, Long boardId, String boardTitle, Long receiverId, String receiverName, String roomNum) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.roomNum = roomNum;
    }
}
