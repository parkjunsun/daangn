package js.daangnclone.web.alarm.activityAlarm.dto;

import js.daangnclone.domain.alarm.activityAlarm.ActivityAlarmType;
import lombok.Builder;
import lombok.Data;

@Data
public class ActivityAlarmResponse {

    private Long id;
    private String message;
    private String link;
    private String sender;
    private String checkedYn;
    private String clickYn;
    private String diffCreatedAt;
    private ActivityAlarmType activityAlarmType;

    @Builder
    public ActivityAlarmResponse(Long id, String message, String link, String sender, String checkedYn, String clickYn, String diffCreatedAt, ActivityAlarmType activityAlarmType) {
        this.id = id;
        this.message = message;
        this.link = link;
        this.sender = sender;
        this.checkedYn = checkedYn;
        this.clickYn = clickYn;
        this.diffCreatedAt = diffCreatedAt;
        this.activityAlarmType = activityAlarmType;
    }

}
