package js.daangnclone.web.chatNotification.dto;

import js.daangnclone.domain.board.BoardStatus;
import lombok.Builder;
import lombok.Data;

@Data
public class ChatListResponse {

    private String link;
    private String opponentName;
    private String opponentAddress;

    private String boardTitle;
    private String boardImage;
    private BoardStatus boardStatus;

    private String lastComment;

    private String checkedYn;

    private String diffCreatedAt;

    @Builder
    public ChatListResponse(String link, String opponentName, String opponentAddress, String boardTitle, String boardImage, BoardStatus boardStatus, String lastComment, String checkedYn, String diffCreatedAt) {
        this.link = link;
        this.opponentName = opponentName;
        this.opponentAddress = opponentAddress;
        this.boardTitle = boardTitle;
        this.boardImage = boardImage;
        this.boardStatus = boardStatus;
        this.lastComment = lastComment;
        this.checkedYn = checkedYn;
        this.diffCreatedAt = diffCreatedAt;
    }
}
