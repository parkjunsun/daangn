package js.daangnclone.service.chatNotification;

import js.daangnclone.domain.chatNotification.ChatNotification;
import js.daangnclone.domain.member.Member;

import java.util.List;

public interface ChatNotificationService {

    void load(ChatNotification chatNotification);
    List<ChatNotification> findChatNotificationList(String roomNum, Member receiver);
    void markAsRead(List<ChatNotification> chatNotificationList);
    void markAsClick(ChatNotification chatNotification);
}
