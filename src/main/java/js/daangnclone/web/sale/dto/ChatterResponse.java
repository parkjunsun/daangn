package js.daangnclone.web.sale.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ChatterResponse {

    private Long senderId;
    private String senderName;
    private String diffLastCommentUpdatedAt;
    private String roomNum;

    @Builder
    public ChatterResponse(Long senderId, String senderName, String diffLastCommentUpdatedAt, String roomNum) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.diffLastCommentUpdatedAt = diffLastCommentUpdatedAt;
        this.roomNum = roomNum;
    }
}
