package js.daangnclone.domain.alarm.activityAlarm;

import js.daangnclone.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ActivityAlarm {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_alarm_id")
    private Long Id;

    private String message;
    private String link;
    private String checkedYn;
    private String clickYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @Enumerated(EnumType.STRING)
    private ActivityAlarmType activityAlarmType;

    private LocalDateTime createdAt;

    @Builder
    public ActivityAlarm(String message, String link, String checkedYn, String clickYn, Member sender, Member receiver, ActivityAlarmType activityAlarmType, LocalDateTime createdAt) {
        this.message = message;
        this.link = link;
        this.checkedYn = checkedYn;
        this.clickYn = clickYn;
        this.sender = sender;
        this.receiver = receiver;
        this.activityAlarmType = activityAlarmType;
        this.createdAt = createdAt;
    }

    public void read() {
        this.checkedYn = "Y";
    }
    public void click() {this.clickYn = "Y";}

}
