package js.daangnclone.web.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ProfileForm {

    private String nickname;
    private String email;
    private String password;

}
