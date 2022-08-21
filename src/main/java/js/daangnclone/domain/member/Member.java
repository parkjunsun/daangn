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
    private String state;
    private String city;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @Builder
    public Member(String username, String password, String nickname, String email, String state, String city, MemberRole memberRole)
    {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.state = state;
        this.city = city;
        this.memberRole = memberRole;
    }

}
