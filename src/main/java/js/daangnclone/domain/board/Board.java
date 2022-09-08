package js.daangnclone.domain.board;

import js.daangnclone.domain.TimeEntity;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.board.dto.InquireBoardDto;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Board extends TimeEntity {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;
    private Long category;
    private int price;
    private String image;
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String content;
    private String detail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void setMember(Member member) {
        this.member = member;
        member.getBoardList().add(this);
    }

    public InquireBoardDto toInquireBoardDto (Board board) {
        return new InquireBoardDto(board.getTitle(), board.getImage(), board.getCategory(), board.getPrice(), board.getContent(), board.getDetail(), board.getCreatedAt(), board.getUpdatedAt());
    }

}
