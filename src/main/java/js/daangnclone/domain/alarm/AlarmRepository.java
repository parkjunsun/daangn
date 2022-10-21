package js.daangnclone.domain.alarm;

import js.daangnclone.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Long> {

//    @Query("select count(a) from Alarm a where a.receiver = :receiver and a.sender <> :receiver and a.checkedYn =:checkedYn")
//    long countByReceiverAndCheckedYn(@Param("receiver") Member receiver, @Param("checkedYn") String checkedYn);
    long countByReceiverAndCheckedYn(Member receiver, String checkedYn);
    List<Alarm> findByReceiverAndCheckedYnOrderByCreatedAtDesc(Member receiver, String checkedYn);
    List<Alarm> findByReceiverAndCreatedAtAfterOrderByCreatedAtDesc(Member receiver, LocalDateTime oneMonthAgo);
    void deleteByReceiverAndCheckedYn(Member receiver, String checkedYn);
}
