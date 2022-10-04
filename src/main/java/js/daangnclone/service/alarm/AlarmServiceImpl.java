package js.daangnclone.service.alarm;

import js.daangnclone.cmn.DateUtil;
import js.daangnclone.domain.alarm.Alarm;
import js.daangnclone.domain.alarm.AlarmRepository;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.alarm.dto.AlarmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
    public void markAsRead(List<Alarm> alarmList) {
        alarmList.forEach(Alarm::read);
    }

    @Override
    public List<AlarmResponse> inquireAlarmList(Member receiver, String checkedYn) {
        List<Alarm> alarmList = alarmRepository.findByReceiverAndCheckedYnOrderByCreatedAtDesc(receiver, checkedYn);

        markAsRead(alarmList);

        return alarmList.stream()
                .map(alarm -> AlarmResponse.builder()
                        .title(alarm.getTitle())
                        .message(alarm.getMessage())
                        .link(alarm.getLink())
                        .sender(alarm.getSender().getNickname())
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
