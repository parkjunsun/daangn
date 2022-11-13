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
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String content;

    @Builder
    public Review(ReviewType reviewType, Member member, Board board, String content) {
        this.reviewType = reviewType;
        this.member = member;
        this.board = board;
        this.content = content;
    }

}
