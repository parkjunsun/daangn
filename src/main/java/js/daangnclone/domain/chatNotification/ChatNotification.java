package js.daangnclone.domain.chatNotification;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatNotification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_notification_id")
    private Long id;

    private String checkedYn;
    private String clickYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private LocalDateTime createdAt;

    @Builder
    public ChatNotification(String checkedYn, String clickYn, Member sender, Member receiver, Board board, LocalDateTime createdAt) {
        this.checkedYn = checkedYn;
        this.clickYn = clickYn;
        this.sender = sender;
        this.receiver = receiver;
        this.board = board;
        this.createdAt = createdAt;
    }

    public void read() {
        this.checkedYn = "Y";
    }

    public void click() {
        this.clickYn = "Y";
    }
}
