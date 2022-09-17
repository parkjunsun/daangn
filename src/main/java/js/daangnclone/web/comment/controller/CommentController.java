package js.daangnclone.web.comment.controller;

import js.daangnclone.domain.area.AreaRepository;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.board.BoardService;
import js.daangnclone.service.comment.CommentService;
import js.daangnclone.service.member.MemberService;
import js.daangnclone.web.comment.dto.CommentForm;
import js.daangnclone.web.comment.dto.CommentWriteInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;
    private final MemberService memberService;
    private final AreaRepository areaRepository;

    @PostMapping("/board/{boardId}/comment/new.do")
    @ResponseBody
    public CommentWriteInfo createComment(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails,
                                @PathVariable("boardId") Long boardId, @ModelAttribute CommentForm commentForm) {

        Board findBoard = boardService.findBoard(boardId);
        Member findMember = memberService.findMember(principalUserDetails.getMember().getId());
        commentService.writeComment(commentForm, findBoard, findMember);

        return createCommentWriteInfo(principalUserDetails, findMember);
    }

    private CommentWriteInfo createCommentWriteInfo(PrincipalUserDetails principalUserDetails, Member findMember) {
        CommentWriteInfo commentWriteInfo = new CommentWriteInfo();
        commentWriteInfo.setMemberId(principalUserDetails.getMember().getId());
        commentWriteInfo.setNickname(findMember.getNickname());
        commentWriteInfo.setCity(areaRepository.findByAreaCd(findMember.getCity()).get().getAreaName());

        return commentWriteInfo;
    }

}
