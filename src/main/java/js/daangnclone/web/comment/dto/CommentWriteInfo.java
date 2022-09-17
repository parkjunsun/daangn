package js.daangnclone.web.comment.dto;

import lombok.Data;

@Data
public class CommentWriteInfo {

    private Long memberId;
    private String nickname;
    private String city;
}
