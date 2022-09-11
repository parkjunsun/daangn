package js.daangnclone.web.member.dto;

import js.daangnclone.domain.member.Member;
import js.daangnclone.domain.member.MemberRole;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class MemberForm {

    @NotBlank(message = "회원 이름은 필수 입니다.")
    private String username;
    @NotBlank(message = "별명은 필수 입니다.")
    private String nickname;
    @NotBlank(message = "비밀번호는 필수 입니다.")
    private String password;
    private String email;
    private Long state;
    private Long city;

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .email(email)
                .state(state)
                .city(city)
                .memberRole(MemberRole.ROLE_ADMIN)
                .certifyYn("N")
                .build();
    }

}
