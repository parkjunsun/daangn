package js.daangnclone.domain.alarm.keywordAlarm;

import js.daangnclone.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface KeywordAlarmRepository extends JpaRepository<KeywordAlarm, Long> {

    long countByReceiverAndCheckedYn(Member receiver, String checkedYn);
    List<KeywordAlarm> findByReceiverAndCreatedAtAfterOrderByCreatedAtDesc(Member receiver, LocalDateTime oneMonthAgo);
}
