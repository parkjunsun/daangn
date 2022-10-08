package js.daangnclone.service.alarm;

import js.daangnclone.domain.alarm.Alarm;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.alarm.dto.AlarmResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface AlarmService {

    void load(Alarm alarm);
    Alarm findAlarm(Long alarmId);
    void markAsRead(List<Alarm> alarmList);
    void markAsClick(Alarm alarm);
    List<AlarmResponse> inquireAlarmList(Member receiver, String checkedYn);
    List<AlarmResponse> inquireAjaxAlarmList(Member receiver);
    void deleteReadAlarm(Member receiver, String checkedYn);

}
