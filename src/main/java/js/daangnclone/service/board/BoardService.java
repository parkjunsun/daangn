package js.daangnclone.service.board;

import js.daangnclone.domain.board.Board;

import java.util.List;

public interface BoardService {

    Board registerItem(Board board);
    List<Board> inquireAllBoardList();
    Board inquireBoard(Long id);
}
