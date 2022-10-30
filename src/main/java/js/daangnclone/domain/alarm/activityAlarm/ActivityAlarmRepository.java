package js.daangnclone.domain.alarm.activityAlarm;

import js.daangnclone.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityAlarmRepository extends JpaRepository<ActivityAlarm, Long> {

//    @Query("select count(a) from Alarm a where a.receiver = :receiver and a.sender <> :receiver and a.checkedYn =:checkedYn")
//    long countByReceiverAndCheckedYn(@Param("receiver") Member receiver, @Param("checkedYn") String checkedYn);
    long countByReceiverAndCheckedYn(Member receiver, String checkedYn);
    List<ActivityAlarm> findByReceiverAndCheckedYnOrderByCreatedAtDesc(Member receiver, String checkedYn);
    List<ActivityAlarm> findByReceiverAndCreatedAtAfterOrderByCreatedAtDesc(Member receiver, LocalDateTime oneMonthAgo);
    void deleteByReceiverAndCheckedYn(Member receiver, String checkedYn);
}
