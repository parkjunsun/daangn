package js.daangnclone.domain.chat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@Document
public class Chat {

    @Id
    private String id;
    private String msg;
    private Long senderId;
    private String senderName;
    private Long boardId;
    private String boardTitle;
    private Long receiverId;
    private String receiverName;

    private String roomNum;

    private LocalDateTime createdAt;

}
