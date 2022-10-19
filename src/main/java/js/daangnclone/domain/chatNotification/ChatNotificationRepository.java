package js.daangnclone.domain.chatNotification;

import js.daangnclone.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatNotificationRepository extends JpaRepository<ChatNotification, Long> {

    long countByReceiverAndCheckedYn(Member receiver, String checkedYn);
    List<ChatNotification> findByRoomNumAndReceiver(String roomNum, Member receiver);
}
