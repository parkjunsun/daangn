package js.daangnclone.web.chatNotification.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class ChatListResponse {

    private String link;
    private String opponentName;
    private String opponentAddress;

    private String boardTitle;
    private String boardImage;

    private String lastComment;

    private String checkedYn;

    private String diffCreatedAt;

    @Builder
    public ChatListResponse(String link, String opponentName, String opponentAddress, String boardTitle, String boardImage, String lastComment, String checkedYn, String diffCreatedAt) {
        this.link = link;
        this.opponentName = opponentName;
        this.opponentAddress = opponentAddress;
        this.boardTitle = boardTitle;
        this.boardImage = boardImage;
        this.lastComment = lastComment;
        this.checkedYn = checkedYn;
        this.diffCreatedAt = diffCreatedAt;
    }
}
