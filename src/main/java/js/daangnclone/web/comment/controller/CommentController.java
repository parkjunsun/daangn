package js.daangnclone.web.comment.controller;

import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.web.comment.dto.CommentForm;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {

    @PostMapping("/board/{boardId}/comment/new")
    public String createComment(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails,
                                @PathVariable("boardId") Long boardId, Model model, CommentForm commentForm) {


    }

}
