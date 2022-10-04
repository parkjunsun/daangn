package js.daangnclone.service.alarm;

import js.daangnclone.domain.alarm.Alarm;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.alarm.dto.AlarmResponse;

import java.util.List;

public interface AlarmService {

    void load(Alarm alarm);
    void markAsRead(List<Alarm> alarmList);
    List<AlarmResponse> inquireAlarmList(Member receiver, String checkedYn);
    void deleteReadAlarm(Member receiver, String checkedYn);

}
