package js.daangnclone.domain.member;

import js.daangnclone.domain.TimeEntity;
import js.daangnclone.domain.attention.Attention;
import js.daangnclone.domain.board.Board;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member extends TimeEntity {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String provider;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private Long state;
    private Long city;
    private String certifyYn;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Attention> attentionList = new ArrayList<>();

    @Builder
    public Member(String provider, String username, String password, String nickname, String email, Long state, Long city, MemberRole memberRole, String certifyYn)
    {
        this.provider = provider;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.state = state;
        this.city = city;
        this.memberRole = memberRole;
        this.certifyYn = certifyYn;
    }

}
