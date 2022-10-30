package js.daangnclone.web.keyword.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class KeywordForm {

    @NotBlank(message = "키워드를 입력해주세요.")
    private String keyword;

}
