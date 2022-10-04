package js.daangnclone.web.alarm.controller;

import js.daangnclone.domain.alarm.Alarm;
import js.daangnclone.domain.alarm.AlarmRepository;
import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.alarm.AlarmService;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.alarm.dto.AlarmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AlarmController {

    private final MemberService memberService;
    private final AlarmRepository alarmRepository;
    private final AlarmService alarmService;

    @GetMapping("/alarmList")
    public String showAlarmList(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        Long memberId = principalUserDetails.getMember().getId();
        Member receiver = memberService.findMember(memberId);

        long numberOfChecked = alarmRepository.countByReceiverAndCheckedYn(receiver, "Y");
        List<AlarmResponse> alarmList = alarmService.inquireAlarmList(receiver, "N");
        putCategorizeAlarmList(model, alarmList, numberOfChecked, alarmList.size());
        model.addAttribute("isNew", true);
        return "alarm/InquireAlarmList";

    }

    @GetMapping("/alarmList/old")
    public String showOldAlarmList(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        Long memberId = principalUserDetails.getMember().getId();
        Member receiver = memberService.findMember(memberId);

        List<AlarmResponse> alarmList = alarmService.inquireAlarmList(receiver, "Y");
        long numberOfNotChecked = alarmRepository.countByReceiverAndCheckedYn(receiver, "N");
        putCategorizeAlarmList(model, alarmList, alarmList.size(), numberOfNotChecked);
        model.addAttribute("isNew", false);
        return "alarm/InquireAlarmList";
    }

    @PostMapping("/alarmList")
    public String deleteAlarmList(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {
        Long memberId = principalUserDetails.getMember().getId();
        Member receiver = memberService.findMember(memberId);
        alarmService.deleteReadAlarm(receiver, "Y");
        return "redirect:/alarmList";
    }

    private void putCategorizeAlarmList(Model model, List<AlarmResponse> alarmList, long numberOfChecked, long numberOfNotChecked) {

        ArrayList<AlarmResponse> newAttentionAlarmList = new ArrayList<>();
        for (AlarmResponse alarm : alarmList) {
            switch (alarm.getAlarmType()) {
                case ATTENTION_CREATED:
                    newAttentionAlarmList.add(alarm);
            }
        }

        model.addAttribute("numberOfNotChecked", numberOfNotChecked);
        model.addAttribute("numberOfChecked", numberOfChecked);
        model.addAttribute("alarmList", alarmList);
        model.addAttribute("newAttentionAlarmList", newAttentionAlarmList);
    }
}
