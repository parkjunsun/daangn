package js.daangnclone.web.comment.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CommentResponse {

    private Long id;
    private String content;
    private String diffCreatedAt;
    private String nickname;
    private String city;
    private String provider;

    @Builder
    public CommentResponse (Long id, String content, String diffCreatedAt, String nickname, String city, String provider) {
        this.id = id;
        this.content = content;
        this.diffCreatedAt = diffCreatedAt;
        this.nickname = nickname;
        this.city = city;
        this.provider = provider;
    }
}
