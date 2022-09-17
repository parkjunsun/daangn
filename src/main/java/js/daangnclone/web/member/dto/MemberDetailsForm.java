package js.daangnclone.web.member.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MemberDetailsForm {

    private Long id;
    @NotBlank(message = "별명은 필수 입니다.")
    private String nickname;
    private Long state;
    private Long city;

}
