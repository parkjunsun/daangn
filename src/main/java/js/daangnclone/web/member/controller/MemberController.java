package js.daangnclone.web.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    @GetMapping("/login")
    public String getLoginForm() {
        return "member/LoginForm";
    }

}
