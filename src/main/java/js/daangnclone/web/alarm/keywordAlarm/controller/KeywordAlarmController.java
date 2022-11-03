package js.daangnclone.web.alarm.keywordAlarm.controller;

import js.daangnclone.domain.alarm.keywordAlarm.KeywordAlarm;
import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.alarm.keywordAlarm.KeywordAlarmService;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.alarm.keywordAlarm.dto.KeywordAlarmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class KeywordAlarmController {

    private final MemberService memberService;
    private final KeywordAlarmService keywordAlarmService;

    @GetMapping("/keywordAlarmList/show.do")
    @ResponseBody
    public List<KeywordAlarmResponse> ajaxShowKeywordAlarmList(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {
        Long memberId = principalUserDetails.getMember().getId();
        Member receiver = memberService.findMember(memberId);

        return keywordAlarmService.inquireAjaxKeywordAlarmList(receiver);
    }

    @PostMapping("keywordAlarmList/click.do")
    @ResponseBody
    public void ajaxReadKeywordAlarm(@RequestParam Long keywordAlarmId) {
        KeywordAlarm keywordAlarm = keywordAlarmService.findKeywordAlarm(keywordAlarmId);
        keywordAlarmService.markAsClick(keywordAlarm);
    }
}
