package js.daangnclone.web.member.dto;
import lombok.Data;

import javax.validation.constraints.NotBlank;


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
//    private Area area;

}
