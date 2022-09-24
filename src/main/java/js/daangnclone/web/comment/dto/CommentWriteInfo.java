package js.daangnclone.web.comment.dto;

import lombok.Data;

@Data
public class CommentWriteInfo {

    private Long commentId;
    private Long memberId;
    private String nickname;
    private String city;
    private String provider;

    private String content;
    private String diffCreatedAt;
}
