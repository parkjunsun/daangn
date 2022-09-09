package js.daangnclone.service.board;

import js.daangnclone.domain.board.Board;
import js.daangnclone.web.board.dto.BoardResponse;

import java.util.List;

public interface BoardService {

    Board registerItem(Board board);
    List<BoardResponse> inquireAllBoardList();
    BoardResponse inquireBoard(Long id);
    void updateView(Long id);
}
