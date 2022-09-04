package js.daangnclone.domain.board;

import js.daangnclone.domain.TimeEntity;
import js.daangnclone.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Board extends TimeEntity {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String category;
    private String price;
    private String image;
    private String content;
    private String detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void setMember(Member member) {
        this.member = member;
        member.getBoardList().add(this);
    }

}
