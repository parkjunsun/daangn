package js.daangnclone.service.board;

import js.daangnclone.Exception.CustomException;
import js.daangnclone.Exception.ErrorCode;
import js.daangnclone.cmn.Category;
import js.daangnclone.cmn.DateUtil;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.board.BoardRepository;
import js.daangnclone.domain.board.BoardStatus;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.board.dto.BoardForm;
import js.daangnclone.web.board.dto.BoardMultiResponse;
import js.daangnclone.web.board.dto.BoardSingleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static js.daangnclone.Exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    @Override
    @Transactional
    public Board registerItem(BoardForm boardForm, Member member) {

        Board board = Board.builder()
                .title(boardForm.getTitle())
                .category(Category.of(boardForm.getCategory()))
                .content(boardForm.getContent().replace("\r\n", "<br>"))
                .detail(boardForm.getDetail())
                .price(boardForm.getPrice())
                .boardStatus(BoardStatus.SALE_ON)
                .member(member)
                .build();

        return boardRepository.save(board);
    }

    @Override
    public List<BoardMultiResponse> inquireAllBoardList() {
        List<Board> findBoardList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        return findBoardList.stream()
                .map(board -> BoardMultiResponse.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .image(board.getImage())
                        .price(board.getPrice())
                        .content(board.getContent())
                        .detail(board.getDetail())
                        .category(board.getCategory().getCategoryName())
                        .diffCreatedAt(DateUtil.diffDate(board.getCreatedAt()))
                        .nickname(board.getMember().getNickname())
                        .city(board.getMember().getArea().getAreaName())
                        .boardStatus(board.getBoardStatus())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public BoardSingleResponse inquireBoard(Long id) {
        Board findBoard = boardRepository.findBoard(id).orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));

        return BoardSingleResponse.builder()
                .id(findBoard.getId())
                .title(findBoard.getTitle())
                .image(findBoard.getImage())
                .price(findBoard.getPrice())
                .content(findBoard.getContent())
                .detail(findBoard.getDetail())
                .category(findBoard.getCategory().getCategoryName())
                .diffCreatedAt(DateUtil.diffDate(findBoard.getCreatedAt()))
                .memberId(findBoard.getMember().getId())
                .nickname(findBoard.getMember().getNickname())
                .city(findBoard.getMember().getArea().getAreaName())
                .view(findBoard.getView())
                .boardStatus(findBoard.getBoardStatus())
                .build();
    }

    @Override
    @Transactional
    public void updateView(Long id) {
        Board findBoard = boardRepository.findById(id).orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));
        findBoard.addView();
    }

    @Override
    @Transactional
    public Board updateBoardStatus(Long id, BoardStatus boardStatus) {
        Board findBoard = boardRepository.findById(id).orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));
        findBoard.setBoardStatus(boardStatus);

        return findBoard;
    }

    @Override
    public Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new CustomException(BOARD_NOT_FOUND));
    }


}
