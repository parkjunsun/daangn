package js.daangnclone.domain.alarm;

import js.daangnclone.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    long countByReceiverAndCheckedYn(Member receiver, String checkedYn);
    List<Alarm> findByReceiverAndCheckedYnOrderByCreatedAtDesc(Member receiver, String checkedYn);
    List<Alarm> findByReceiverAndCreatedAtAfterOrderByCreatedAtDesc(Member receiver, LocalDateTime oneMonthAgo);
    void deleteByReceiverAndCheckedYn(Member receiver, String checkedYn);
}
