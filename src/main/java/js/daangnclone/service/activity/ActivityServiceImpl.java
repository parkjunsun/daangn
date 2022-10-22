package js.daangnclone.service.activity;

import js.daangnclone.cmn.DateUtil;
import js.daangnclone.domain.activity.Activity;
import js.daangnclone.domain.activity.ActivityRepository;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.activity.dto.ActivityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActivityServiceImpl implements ActivityService{

    private final ActivityRepository activityRepository;

    @Override
    @Transactional
    public Activity load(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public List<ActivityResponse> inquireActivityList(Member sender) {
        List<Activity> activityList = activityRepository.findBySenderOrderByCreatedAtDesc(sender);

        return activityList.stream()
                .map(activity -> ActivityResponse.builder()
                        .senderName(activity.getSender().getNickname())
                        .receiverName(activity.getReceiver().getNickname())
                        .link(activity.getLink())
                        .message(activity.getMessage())
                        .content(activity.getContent())
                        .activityType(activity.getActivityType())
                        .diffCreatedAt(activity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                        .build())
                .collect(Collectors.toList());
    }
}
