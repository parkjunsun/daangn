package js.daangnclone.web.keyword.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class KeywordResponse {

    private Long id;
    private String word;

    @Builder
    public KeywordResponse(Long id, String word) {
        this.id = id;
        this.word = word;
    }
}
