package js.daangnclone.service.board;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    @Override
    public Board registerItem(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public List<Board> inquireAllBoardList() {
        return boardRepository.findAll();
    }
}
