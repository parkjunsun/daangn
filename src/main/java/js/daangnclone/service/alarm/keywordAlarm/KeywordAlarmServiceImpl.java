package js.daangnclone.service.alarm.keywordAlarm;

import js.daangnclone.Exception.CustomException;
import js.daangnclone.Exception.ErrorCode;
import js.daangnclone.cmn.DateUtil;
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
import java.util.stream.Collectors;

import static js.daangnclone.Exception.ErrorCode.KEYWORD_ALARM_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KeywordAlarmServiceImpl implements KeywordAlarmService{

    private final KeywordAlarmRepository keywordAlarmRepository;


    @Override
    public KeywordAlarm findKeywordAlarm(Long keywordAlarmId) {
        return keywordAlarmRepository.findById(keywordAlarmId).orElseThrow(() -> new CustomException(KEYWORD_ALARM_NOT_FOUND));
    }

    @Override
    @Transactional
    public KeywordAlarm load(KeywordAlarm keywordAlarm) {
        return keywordAlarmRepository.save(keywordAlarm);
    }

    @Override
    @Transactional
    public void markAsRead(List<KeywordAlarm> keywordAlarmList) {
        keywordAlarmList.forEach(KeywordAlarm::read);
    }

    @Override
    @Transactional
    public void markAsClick(KeywordAlarm keywordAlarm) {
        keywordAlarm.click();
    }

    @Override
    @Transactional
    public List<KeywordAlarmResponse> inquireAjaxKeywordAlarmList(Member receiver) {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<KeywordAlarm> keywordAlarmList = keywordAlarmRepository.findByReceiverAndCreatedAtAfterOrderByCreatedAtDesc(receiver, oneMonthAgo);

        markAsRead(keywordAlarmList);

        return keywordAlarmList.stream()
                .map(keywordAlarm -> KeywordAlarmResponse.builder()
                        .id(keywordAlarm.getId())
                        .link("/board/" + keywordAlarm.getBoard().getId())
                        .keyword(keywordAlarm.getKeyword())
                        .boardImage(keywordAlarm.getBoard().getImage())
                        .boardTitle(keywordAlarm.getBoard().getTitle())
                        .areaName(keywordAlarm.getBoard().getMember().getArea().getAreaName())
                        .checkedYn(keywordAlarm.getCheckedYn())
                        .clickYn(keywordAlarm.getClickYn())
                        .diffCreatedAt(DateUtil.diffDate(keywordAlarm.getBoard().getCreatedAt()))
                        .build())
                .collect(Collectors.toList());
    }
}
