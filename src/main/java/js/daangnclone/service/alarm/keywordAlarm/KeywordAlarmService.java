package js.daangnclone.service.alarm.keywordAlarm;

import js.daangnclone.domain.alarm.activityAlarm.ActivityAlarm;
import js.daangnclone.domain.alarm.keywordAlarm.KeywordAlarm;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.alarm.keywordAlarm.dto.KeywordAlarmResponse;

import java.util.List;

public interface KeywordAlarmService {

    KeywordAlarm findKeywordAlarm(Long keywordAlarmId);
    KeywordAlarm load(KeywordAlarm keywordAlarm);
    void markAsRead(List<KeywordAlarm> activityAlarmList);
    void markAsClick(KeywordAlarm activityAlarm);
    List<KeywordAlarmResponse> inquireAjaxKeywordAlarmList(Member receiver);

}
