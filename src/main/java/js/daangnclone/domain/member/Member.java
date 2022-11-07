package js.daangnclone.domain.member;

import js.daangnclone.cmn.area.Area;
import js.daangnclone.domain.TimeEntity;
import js.daangnclone.domain.attention.Attention;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.comment.Comment;
import js.daangnclone.domain.like.Likes;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends TimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String provider;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String certifyYn;

    @Enumerated(EnumType.STRING)
    private Area area;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;



    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Attention> attentionList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Likes> likeList = new ArrayList<>();

    @Builder
    public Member(String provider, String username, String password, String nickname, String email, Area area, MemberRole memberRole, String certifyYn)
    {
        this.provider = provider;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.area = area;
        this.memberRole = memberRole;
        this.certifyYn = certifyYn;
    }

}
