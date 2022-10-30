package js.daangnclone.service.alarm.activityAlarm;

import js.daangnclone.domain.alarm.activityAlarm.ActivityAlarm;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.alarm.activityAlarm.dto.ActivityAlarmResponse;

import java.util.List;

public interface ActivityAlarmService {

    void load(ActivityAlarm activityAlarm);
    ActivityAlarm findAlarm(Long alarmId);
    void markAsRead(List<ActivityAlarm> activityAlarmList);
    void markAsClick(ActivityAlarm activityAlarm);
    List<ActivityAlarmResponse> inquireAlarmList(Member receiver, String checkedYn);
    List<ActivityAlarmResponse> inquireAjaxAlarmList(Member receiver);
    void deleteReadAlarm(Member receiver, String checkedYn);

}
