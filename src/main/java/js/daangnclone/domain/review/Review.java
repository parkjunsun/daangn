package js.daangnclone.domain.review;

import js.daangnclone.domain.TimeEntity;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import lombok.*;

import javax.persistence.*;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends TimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReviewType reviewType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private int reviewScore;
    private String reviews;
    private String content;

    @Builder
    public Review(ReviewType reviewType, Member sender, Member receiver, Board board, int reviewScore, String reviews, String content) {
        this.reviewType = reviewType;
        this.sender = sender;
        this.receiver = receiver;
        this.board = board;
        this.reviewScore = reviewScore;
        this.reviews = reviews;
        this.content = content;
    }

}
