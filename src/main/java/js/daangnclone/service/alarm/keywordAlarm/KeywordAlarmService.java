package js.daangnclone.service.alarm.keywordAlarm;

import js.daangnclone.domain.alarm.keywordAlarm.KeywordAlarm;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.alarm.keywordAlarm.dto.KeywordAlarmResponse;

import java.util.List;

public interface KeywordAlarmService {

    KeywordAlarm load(KeywordAlarm keywordAlarm);
//    List<KeywordAlarmResponse> inquireAjaxKeywordAlarmList(Member receiver);

}
