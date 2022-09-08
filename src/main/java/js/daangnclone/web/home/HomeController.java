package js.daangnclone.web.home;

import js.daangnclone.domain.board.Board;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        List<Board> boardList = boardService.inquireAllBoardList();
        int rowSize = (boardList.size() / 4) + 1;
        model.addAttribute("rowSize", rowSize);
        model.addAttribute("boardList", boardList);
        return "index";
    }

}
