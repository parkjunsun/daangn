package js.daangnclone.service.alarm.activityAlarm;

import js.daangnclone.Exception.CustomException;
import js.daangnclone.cmn.DateUtil;
import js.daangnclone.domain.alarm.activityAlarm.ActivityAlarm;
import js.daangnclone.domain.alarm.activityAlarm.ActivityAlarmRepository;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.alarm.activityAlarm.dto.ActivityAlarmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static js.daangnclone.Exception.ErrorCode.ALARM_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class ActivityAlarmServiceImpl implements ActivityAlarmService {

    private final ActivityAlarmRepository activityAlarmRepository;

    @Override
    public void load(ActivityAlarm activityAlarm) {
        activityAlarmRepository.save(activityAlarm);
    }

    @Override
    public ActivityAlarm findAlarm(Long alarmId) {
        return activityAlarmRepository.findById(alarmId).orElseThrow(() -> new CustomException(ALARM_NOT_FOUND));
    }

    @Override
    public void markAsRead(List<ActivityAlarm> activityAlarmList) {
        activityAlarmList.forEach(ActivityAlarm::read);
    }

    @Override
    public void markAsClick(ActivityAlarm activityAlarm) {
        activityAlarm.click();
    }

    @Override
    public List<ActivityAlarmResponse> inquireAlarmList(Member receiver, String checkedYn) {
        List<ActivityAlarm> activityAlarmList = activityAlarmRepository.findByReceiverAndCheckedYnOrderByCreatedAtDesc(receiver, checkedYn);

        markAsRead(activityAlarmList);

        return activityAlarmList.stream()
                .map(alarm -> ActivityAlarmResponse.builder()
                        .id(alarm.getId())
                        .message(alarm.getMessage())
                        .link(alarm.getLink())
                        .sender(alarm.getSender().getNickname())
                        .checkedYn(alarm.getCheckedYn())
                        .clickYn(alarm.getClickYn())
                        .activityAlarmType(alarm.getActivityAlarmType())
                        .diffCreatedAt(DateUtil.diffDate(alarm.getCreatedAt()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<ActivityAlarmResponse> inquireAjaxAlarmList(Member receiver) {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<ActivityAlarm> activityAlarmList = activityAlarmRepository.findByReceiverAndCreatedAtAfterOrderByCreatedAtDesc(receiver, oneMonthAgo);

        markAsRead(activityAlarmList);

        return activityAlarmList.stream()
                .map(alarm -> ActivityAlarmResponse.builder()
                        .id(alarm.getId())
                        .message(alarm.getMessage())
                        .link(alarm.getLink())
                        .sender(alarm.getSender().getNickname())
                        .checkedYn(alarm.getCheckedYn())
                        .clickYn(alarm.getClickYn())
                        .activityAlarmType(alarm.getActivityAlarmType())
                        .diffCreatedAt(DateUtil.diffDate(alarm.getCreatedAt()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteReadAlarm(Member receiver, String checkedYn) {
        activityAlarmRepository.deleteByReceiverAndCheckedYn(receiver, checkedYn);
    }

    @Override
    @Transactional
    public void deleteActivityAlarm(ActivityAlarm activityAlarm) {
        activityAlarmRepository.delete(activityAlarm);
    }
}
