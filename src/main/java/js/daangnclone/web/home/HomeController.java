package js.daangnclone.web.home;

import js.daangnclone.security.PrincipalUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {

        System.out.println("@@@@@@@@@@@@@@");
        System.out.println(principalUserDetails.getMember().getUsername());

        return "index";
    }

}
