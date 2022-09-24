package js.daangnclone.web.attention.controller;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.attention.AttentionService;
import js.daangnclone.service.board.BoardService;
import js.daangnclone.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class AttentionController {

    private final MemberService memberService;
    private final BoardService boardService;
    private final AttentionService attentionService;

    //ajax 데이터
    @PostMapping("/board/{boardId}/attention/process.do")
    @ResponseBody
    public String createAttention(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, @PathVariable("boardId") Long boardId) {
        Long memberId = principalUserDetails.getMember().getId();
        Member findMember = memberService.findMember(memberId);
        Board findBoard = boardService.findBoard(boardId);

        attentionService.processAttention(findMember, findBoard);

        return attentionService.getInpAttentionYn(findMember, findBoard);
    }
}
