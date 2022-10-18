package js.daangnclone.web.chatNotification.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ChatListResponse {

    private String link;
    private String opponentName;
    private String opponentAddress;

    private String boardImage;

    @Builder
    public ChatListResponse(String link, String opponentName, String opponentAddress, String boardImage) {
        this.link = link;
        this.opponentName = opponentName;
        this.opponentAddress = opponentAddress;
        this.boardImage = boardImage;
    }
}
