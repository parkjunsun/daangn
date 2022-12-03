package js.daangnclone.web.activity.controller;

import js.daangnclone.cmn.DateUtil;
import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.activity.ActivityService;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.activity.dto.ActivityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;
    private final MemberService memberService;

    @GetMapping("/activityList")
    public String inquireActivityList(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        Long memberId = principalUserDetails.getMember().getId();
        Member findMember = memberService.findMember(memberId);

        List<ActivityResponse> activityList = activityService.inquireActivityList(findMember);

        putCategorizeActivityList(model, activityList);
//        model.addAttribute("nickname", findMember.getNickname());
//        model.addAttribute("certifyYn", findMember.getCertifyYn());

        return "activity/InquireActivityList";
    }

    private void putCategorizeActivityList(Model model, List<ActivityResponse> activityList) {

        ArrayList<ActivityResponse> newBoardActivityList = new ArrayList<>();
        ArrayList<ActivityResponse> newAttentionActivityList = new ArrayList<>();
        ArrayList<ActivityResponse> newCommentActivityList = new ArrayList<>();
        ArrayList<ActivityResponse> newLikesActivityList = new ArrayList<>();
        ArrayList<ActivityResponse> newChatActivityList  = new ArrayList<>();
        ArrayList<ActivityResponse> newReviewActivityList = new ArrayList<>();

        for (ActivityResponse activity : activityList) {
            switch (activity.getActivityType()) {
                case BOARD_CREATE:
                    newBoardActivityList.add(activity);
                    break;
                case ATTENTION_CREATE:
                    newAttentionActivityList.add(activity);
                    break;
                case COMMENT_CREATE:
                    newCommentActivityList.add(activity);
                    break;
                case LIKES_CREATE:
                    newLikesActivityList.add(activity);
                    break;
                case CHAT_CREATE:
                    newChatActivityList.add(activity);
                    break;
                case REVIEW_CREATE:
                    newReviewActivityList.add(activity);
            }
        }


        model.addAttribute("activityList", activityList);
        model.addAttribute("newBoardActivityList", newBoardActivityList);
        model.addAttribute("newAttentionActivityList", newAttentionActivityList);
        model.addAttribute("newCommentActivityList", newCommentActivityList);
        model.addAttribute("newLikesActivityList", newLikesActivityList);
        model.addAttribute("newChatActivityList", newChatActivityList);
        model.addAttribute("newReviewActivityList", newReviewActivityList);
    }

}
