package js.daangnclone.web.alarm.dto;

import js.daangnclone.domain.alarm.AlarmType;
import lombok.Builder;
import lombok.Data;

@Data
public class AlarmResponse {

    private Long id;
    private String message;
    private String link;
    private String sender;
    private String checkedYn;
    private String clickYn;
    private String diffCreatedAt;
    private AlarmType alarmType;

    @Builder
    public AlarmResponse (Long id, String message, String link, String sender, String checkedYn, String clickYn, String diffCreatedAt, AlarmType alarmType) {
        this.id = id;
        this.message = message;
        this.link = link;
        this.sender = sender;
        this.checkedYn = checkedYn;
        this.clickYn = clickYn;
        this.diffCreatedAt = diffCreatedAt;
        this.alarmType = alarmType;
    }

}
