package js.daangnclone.domain.alarm;

import js.daangnclone.domain.TimeEntity;
import js.daangnclone.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long Id;

    private String title;
    private String message;
    private String link;
    private String checkedYn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    private LocalDateTime createdAt;

    @Builder
    public Alarm(String title, String message, String link, String checkedYn, Member sender, Member receiver, AlarmType alarmType, LocalDateTime createdAt) {
        this.title = title;
        this.message = message;
        this.link = link;
        this.checkedYn = checkedYn;
        this.sender = sender;
        this.receiver = receiver;
        this.alarmType = alarmType;
        this.createdAt = createdAt;
    }

    public void read() {
        this.checkedYn = "Y";
    }

}
