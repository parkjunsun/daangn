package js.daangnclone.web.activity.dto;

import js.daangnclone.domain.activity.ActivityType;
import lombok.Builder;
import lombok.Data;

@Data
public class ActivityResponse {

    private String senderName;
    private String receiverName;
    private String link;
    private String message;
    private String content;
    private ActivityType activityType;
    private String diffCreatedAt;

    @Builder
    public ActivityResponse(String senderName, String receiverName, String link, String message, String content, ActivityType activityType, String diffCreatedAt) {
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.link = link;
        this.message = message;
        this.content = content;
        this.activityType = activityType;
        this.diffCreatedAt = diffCreatedAt;
    }

}
