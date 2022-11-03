package js.daangnclone.web.alarm.keywordAlarm.dto;

import js.daangnclone.domain.board.Board;
import lombok.Builder;
import lombok.Data;

@Data
public class KeywordAlarmResponse {

    private Long id;
    private String link;
    private String keyword;
    private String boardImage;
    private String boardTitle;
    private String areaName;
    private String checkedYn;
    private String clickYn;
    private String diffCreatedAt;

    @Builder
    public KeywordAlarmResponse(Long id, String link, String keyword, String boardImage, String boardTitle, String areaName, String checkedYn, String clickYn, String diffCreatedAt) {
        this.id = id;
        this.link = link;
        this.keyword = keyword;
        this.boardImage = boardImage;
        this.boardTitle = boardTitle;
        this.areaName = areaName;
        this.checkedYn = checkedYn;
        this.clickYn = clickYn;
        this.diffCreatedAt = diffCreatedAt;
    }

}
