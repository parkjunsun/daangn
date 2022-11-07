package js.daangnclone.domain.attention;

import js.daangnclone.domain.TimeEntity;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attention extends TimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attention_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Attention(Member member, Board board) {
        setMember(member);
        setBoard(board);
    }

    public void setMember(Member member) {
        this.member = member;
        member.getAttentionList().add(this);
    }

    public void setBoard(Board board) {
        this.board = board;
        board.getAttentionList().add(this);
    }

}
