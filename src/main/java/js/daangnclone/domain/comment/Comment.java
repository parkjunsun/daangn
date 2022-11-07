package js.daangnclone.domain.comment;

import js.daangnclone.domain.TimeEntity;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.like.Likes;
import js.daangnclone.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends TimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
    private List<Likes> likeList = new ArrayList<>();

    @Builder
    public Comment(String content, Member member, Board board) {
        this.content = content;
        setMember(member);
        setBoard(board);
    }

    public void setMember(Member member) {
        this.member = member;
        member.getCommentList().add(this);
    }

    public void setBoard(Board board) {
        this.board = board;
        board.getCommentList().add(this);
    }
}
