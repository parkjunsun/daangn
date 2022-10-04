package js.daangnclone.web.alarm.dto;

import js.daangnclone.domain.alarm.AlarmType;
import lombok.Builder;
import lombok.Data;

@Data
public class AlarmResponse {

    private String title;
    private String message;
    private String link;
    private String sender;
    private String diffCreatedAt;
    private AlarmType alarmType;

    @Builder
    public AlarmResponse (String title, String message, String link, String sender, String diffCreatedAt, AlarmType alarmType) {
        this.title = title;
        this.message = message;
        this.link = link;
        this.sender = sender;
        this.diffCreatedAt = diffCreatedAt;
        this.alarmType = alarmType;
    }

}
