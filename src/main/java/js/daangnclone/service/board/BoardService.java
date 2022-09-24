package js.daangnclone.service.board;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.board.dto.BoardForm;
import js.daangnclone.web.board.dto.BoardMultiResponse;
import js.daangnclone.web.board.dto.BoardSingleResponse;

import java.util.List;

public interface BoardService {

    Board registerItem(BoardForm boardForm, Member member);
    List<BoardMultiResponse> inquireAllBoardList();
    BoardSingleResponse inquireBoard(Long id);
    void updateView(Long id);

    Board findBoard(Long id);
}
