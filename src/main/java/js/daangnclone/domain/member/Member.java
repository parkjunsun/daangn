package js.daangnclone.domain.member;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;
    private String nickname;
    private String password;
    private String email;
    private Long state;
    private Long city;
    private String certifyYn;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Builder
    public Member(String username, String password, String nickname, String email, Long state, Long city, MemberRole memberRole, String certifyYn)
    {
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
