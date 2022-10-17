package js.daangnclone.service.chatNotification;

import js.daangnclone.domain.chatNotification.ChatNotification;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.chatNotification.dto.ChatNotificationResponse;

import java.util.List;

public interface ChatNotificationService {

    void load(ChatNotification chatNotification);
    ChatNotification findChatNotification(Long chatNotificationId);
    void markAsRead(List<ChatNotification> chatNotificationList);
    void markAsClick(ChatNotification chatNotification);
    List<ChatNotificationResponse> inquireChatNotificationList(Member receiver, String checkedYn);
}
