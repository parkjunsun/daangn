package js.daangnclone.web.home;

import js.daangnclone.domain.board.Board;
import js.daangnclone.security.PrincipalUserDetails;
import js.daangnclone.service.board.BoardService;
import js.daangnclone.web.board.dto.InquireBoardDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails, Model model) {
        List<Board> findBoardList = boardService.inquireAllBoardList();
        List<InquireBoardDao> boardList = findBoardList.stream()
                .map(board -> new InquireBoardDao(board.getId(), board.getTitle(), board.getImage(), board.getCategory(), board.getPrice(), board.getContent(), board.getDetail(), board.getCreatedAt(), board.getUpdatedAt()))
                .collect(Collectors.toList());
        model.addAttribute("boardList", boardList);
        return "index";
    }

}
