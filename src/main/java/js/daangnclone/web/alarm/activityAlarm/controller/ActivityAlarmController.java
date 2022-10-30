package js.daangnclone.web.alarm.activityAlarm.controller;

import js.daangnclone.domain.alarm.activityAlarm.ActivityAlarm;
import js.daangnclone.domain.alarm.activityAlarm.ActivityAlarmRepository;
import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.alarm.activityAlarm.ActivityAlarmService;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.alarm.activityAlarm.dto.ActivityAlarmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ActivityAlarmController {

    private final MemberService memberService;
    private final ActivityAlarmRepository activityAlarmRepository;
    private final ActivityAlarmService activityAlarmService;

    @GetMapping("/activityAlarmList")
    public String showAlarmList(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        Long memberId = principalUserDetails.getMember().getId();
        Member receiver = memberService.findMember(memberId);

        long numberOfChecked = activityAlarmRepository.countByReceiverAndCheckedYn(receiver, "Y");
        List<ActivityAlarmResponse> alarmList = activityAlarmService.inquireAlarmList(receiver, "N");
        putCategorizeAlarmList(model, alarmList, numberOfChecked, alarmList.size());
        model.addAttribute("isNew", true);
        model.addAttribute("certifyYn", receiver.getCertifyYn());
        return "alarm/InquireActivityAlarmList";

    }

    @GetMapping("/activityAlarmList/show.do")
    @ResponseBody
    public List<ActivityAlarmResponse> ajaxShowAlarmList(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {
        Long receiverId = principalUserDetails.getMember().getId();
        Member receiver = memberService.findMember(receiverId);
        return activityAlarmService.inquireAjaxAlarmList(receiver);
    }

    @PostMapping("/activityAlarmList/click.do")
    @ResponseBody
    public void ajaxReadAlarm(@RequestParam Long alarmId) {
        ActivityAlarm activityAlarm = activityAlarmService.findAlarm(alarmId);
        activityAlarmService.markAsClick(activityAlarm);
    }

    @GetMapping("/activityAlarmList/old")
    public String showOldAlarmList(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        Long memberId = principalUserDetails.getMember().getId();
        Member receiver = memberService.findMember(memberId);

        List<ActivityAlarmResponse> alarmList = activityAlarmService.inquireAlarmList(receiver, "Y");
        long numberOfNotChecked = activityAlarmRepository.countByReceiverAndCheckedYn(receiver, "N");
        putCategorizeAlarmList(model, alarmList, alarmList.size(), numberOfNotChecked);
        model.addAttribute("isNew", false);
        model.addAttribute("certifyYn", receiver.getCertifyYn());
        return "alarm/InquireActivityAlarmList";
    }

    @PostMapping("/activityAlarmList")
    public String deleteAlarmList(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {
        Long memberId = principalUserDetails.getMember().getId();
        Member receiver = memberService.findMember(memberId);
        activityAlarmService.deleteReadAlarm(receiver, "Y");
        return "redirect:/activityAlarmList";
    }

    private void putCategorizeAlarmList(Model model, List<ActivityAlarmResponse> activityAlarmList, long numberOfChecked, long numberOfNotChecked) {

        ArrayList<ActivityAlarmResponse> newAttentionAlarmList = new ArrayList<>();
        ArrayList<ActivityAlarmResponse> newCommentAlarmList = new ArrayList<>();
        ArrayList<ActivityAlarmResponse> newLikesAlarmList = new ArrayList<>();
        for (ActivityAlarmResponse alarm : activityAlarmList) {
            switch (alarm.getActivityAlarmType()) {
                case ATTENTION_CREATED:
                    newAttentionAlarmList.add(alarm);
                    break;
                case COMMENT_CREATED:
                    newCommentAlarmList.add(alarm);
                    break;
                case LIKES_CREATED:
                    newLikesAlarmList.add(alarm);
                    break;
            }
        }

        model.addAttribute("numberOfNotChecked", numberOfNotChecked);
        model.addAttribute("numberOfChecked", numberOfChecked);
        model.addAttribute("activityAlarmList", activityAlarmList);
        model.addAttribute("newAttentionAlarmList", newAttentionAlarmList);
        model.addAttribute("newCommentAlarmList", newCommentAlarmList);
        model.addAttribute("newLikesAlarmList", newLikesAlarmList);
    }
}
