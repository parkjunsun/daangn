package js.daangnclone.domain.alarm.keywordAlarm;

import js.daangnclone.domain.TimeEntity;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KeywordAlarm {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyword_alarm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String checkedYn;
    private String clickYn;

    private LocalDateTime createdAt;

    @Builder
    public KeywordAlarm(Member receiver, Board board, String checkedYn, String clickYn, LocalDateTime createdAt) {
        this.receiver = receiver;
        this.board = board;
        this.checkedYn = checkedYn;
        this.clickYn = clickYn;
        this.createdAt = createdAt;
    }
}
