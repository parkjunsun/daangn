package js.daangnclone.domain.like;

import js.daangnclone.domain.TimeEntity;
import js.daangnclone.domain.comment.Comment;
import js.daangnclone.domain.member.Member;
import lombok.*;

import javax.persistence.*;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "likes")
public class Likes extends TimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Builder
    public Likes(Member member, Comment comment) {
        setMember(member);
        setComment(comment);
    }

    public void setMember(Member member) {
        this.member = member;
        member.getLikeList().add(this);
    }

    public void setComment(Comment comment) {
        this.comment = comment;
        comment.getLikeList().add(this);
    }

}
