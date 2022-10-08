package js.daangnclone.service.alarm;

import js.daangnclone.Exception.CustomException;
import js.daangnclone.Exception.ErrorCode;
import js.daangnclone.cmn.DateUtil;
import js.daangnclone.domain.alarm.Alarm;
import js.daangnclone.domain.alarm.AlarmRepository;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.alarm.dto.AlarmResponse;
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
public class AlarmServiceImpl implements AlarmService{

    private final AlarmRepository alarmRepository;

    @Override
    public void load(Alarm alarm) {
        alarmRepository.save(alarm);
    }

    @Override
    public Alarm findAlarm(Long alarmId) {
        return alarmRepository.findById(alarmId).orElseThrow(() -> new CustomException(ALARM_NOT_FOUND));
    }

    @Override
    public void markAsRead(List<Alarm> alarmList) {
        alarmList.forEach(Alarm::read);
    }

    @Override
    public void markAsClick(Alarm alarm) {
        alarm.click();
    }

    @Override
    public List<AlarmResponse> inquireAlarmList(Member receiver, String checkedYn) {
        List<Alarm> alarmList = alarmRepository.findByReceiverAndCheckedYnOrderByCreatedAtDesc(receiver, checkedYn);

        markAsRead(alarmList);

        return alarmList.stream()
                .map(alarm -> AlarmResponse.builder()
                        .id(alarm.getId())
                        .message(alarm.getMessage())
                        .link(alarm.getLink())
                        .sender(alarm.getSender().getNickname())
                        .checkedYn(alarm.getCheckedYn())
                        .clickYn(alarm.getClickYn())
                        .alarmType(alarm.getAlarmType())
                        .diffCreatedAt(DateUtil.diffDate(alarm.getCreatedAt()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<AlarmResponse> inquireAjaxAlarmList(Member receiver) {
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        List<Alarm> alarmList = alarmRepository.findByReceiverAndCreatedAtAfterOrderByCreatedAtDesc(receiver, oneMonthAgo);

        markAsRead(alarmList);

        return alarmList.stream()
                .map(alarm -> AlarmResponse.builder()
                        .id(alarm.getId())
                        .message(alarm.getMessage())
                        .link(alarm.getLink())
                        .sender(alarm.getSender().getNickname())
                        .checkedYn(alarm.getCheckedYn())
                        .clickYn(alarm.getClickYn())
                        .alarmType(alarm.getAlarmType())
                        .diffCreatedAt(DateUtil.diffDate(alarm.getCreatedAt()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteReadAlarm(Member receiver, String checkedYn) {
        alarmRepository.deleteByReceiverAndCheckedYn(receiver, checkedYn);
    }
}
