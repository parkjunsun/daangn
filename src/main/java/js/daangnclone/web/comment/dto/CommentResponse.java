package js.daangnclone.web.comment.dto;

import js.daangnclone.domain.like.Likes;
import js.daangnclone.web.like.dto.LikesResponse;
import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CommentResponse {

    private Long id;
    private String content;
    private String diffCreatedAt;
    private String nickname;
    private String city;
    private String provider;
    private String likeInpYn;
    private Long likeCnt;

    @Builder
    public CommentResponse (Long id, String content, String diffCreatedAt, String nickname, String city, String provider, String likeInpYn, Long likeCnt) {
        this.id = id;
        this.content = content;
        this.diffCreatedAt = diffCreatedAt;
        this.nickname = nickname;
        this.city = city;
        this.provider = provider;
        this.likeInpYn = likeInpYn;
        this.likeCnt = likeCnt;
    }
}
