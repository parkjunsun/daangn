package js.daangnclone.web.member.dto;

import js.daangnclone.domain.member.Member;
import js.daangnclone.domain.member.MemberRole;
import lombok.Data;

@Data
public class CreateMemberDto {

    private String username;
    private String nickname;
    private String password;
    private String email;
    private String address;
    private String address2;

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .email(email)
                .address(address)
                .address2(address2)
                .memberRole(MemberRole.ROLE_ADMIN)
                .build();
    }

}
