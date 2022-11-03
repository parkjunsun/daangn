package js.daangnclone.domain.activity;

import js.daangnclone.domain.TimeEntity;
import js.daangnclone.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Activity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long id;

    private String message;
    private String content;
    private String link;

    @Enumerated(EnumType.STRING)
    private ActivityType activityType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    private LocalDateTime createdAt;

    @Builder
    public Activity(String message, String content, String link, ActivityType activityType, Member sender, Member receiver, LocalDateTime createdAt) {
        this.message = message;
        this.content = content;
        this.link = link;
        this.activityType = activityType;
        this.sender = sender;
        this.receiver = receiver;
    }
}
