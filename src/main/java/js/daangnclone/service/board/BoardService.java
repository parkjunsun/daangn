package js.daangnclone.service.board;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.board.dto.BoardForm;
import js.daangnclone.web.board.dto.BoardResponse;

import java.util.List;

public interface BoardService {

    Board registerItem(BoardForm boardForm, Member member);
    List<BoardResponse> inquireAllBoardList();
    BoardResponse inquireBoard(Long id);
    void updateView(Long id);

    Board findBoard(Long id);
}
