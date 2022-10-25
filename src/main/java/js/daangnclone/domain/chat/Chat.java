package js.daangnclone.domain.chat;

import js.daangnclone.domain.TimeEntity;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.chatNotification.ChatNotification;
import js.daangnclone.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat extends TimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long id;

    private String roomNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Member seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyerId")
    private Member buyer;

    private String lastComment;
    private LocalDateTime lastCommentUpdatedAt;

    @Builder
    public Chat(String roomNum, Board board, Member seller, Member buyer, String lastComment, LocalDateTime lastCommentUpdatedAt) {
        this.roomNum = roomNum;
        this.board = board;
        this.seller = seller;
        this.buyer = buyer;
        this.lastComment = lastComment;
        this.lastCommentUpdatedAt = lastCommentUpdatedAt;
    }
}
