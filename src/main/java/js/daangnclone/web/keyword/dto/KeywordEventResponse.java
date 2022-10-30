package js.daangnclone.web.keyword.dto;

import js.daangnclone.domain.member.Member;
import lombok.Builder;
import lombok.Data;

@Data
public class KeywordEventResponse {

    private Long id;
    private String word;
    private Member member;

    @Builder
    public KeywordEventResponse(Long id, String word, Member member) {
        this.id = id;
        this.word = word;
        this.member = member;
    }

}
