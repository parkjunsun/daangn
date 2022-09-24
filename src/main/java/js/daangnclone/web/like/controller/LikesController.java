package js.daangnclone.web.like.controller;

import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.like.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likeService;

    @PostMapping("/comment/{commentId}/like/process.do")
    @ResponseBody
    public String processLike(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, @PathVariable("commentId") Long commentId) {

        Long memberId = principalUserDetails.getMember().getId();
        likeService.processLike(memberId, commentId);

        return likeService.getInpLikeYn(memberId, commentId);
    }
}
