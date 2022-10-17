package js.daangnclone.domain.chatNotification;

import js.daangnclone.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatNotificationRepository extends JpaRepository<ChatNotification, Long> {

    long countByReceiverAndCheckedYn(Member receiver, String checkedYn);
}
