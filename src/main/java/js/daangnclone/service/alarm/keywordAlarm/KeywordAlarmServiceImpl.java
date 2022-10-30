package js.daangnclone.service.alarm.keywordAlarm;

import js.daangnclone.domain.alarm.keywordAlarm.KeywordAlarm;
import js.daangnclone.domain.alarm.keywordAlarm.KeywordAlarmRepository;
import js.daangnclone.domain.keyword.KeywordRepository;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.alarm.keywordAlarm.dto.KeywordAlarmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KeywordAlarmServiceImpl implements KeywordAlarmService{

    private final KeywordAlarmRepository keywordAlarmRepository;

    @Override
    @Transactional
    public KeywordAlarm load(KeywordAlarm keywordAlarm) {
        return keywordAlarmRepository.save(keywordAlarm);
    }

//    @Override
//    public List<KeywordAlarmResponse> inquireAjaxKeywordAlarmList(Member receiver) {
//        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
//        List<KeywordAlarm> keywordAlarmList = keywordAlarmRepository.findByReceiverAndCreatedAtAfterOrderByCreatedAtDesc(receiver, oneMonthAgo);
//
//    }
}
