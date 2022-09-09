package js.daangnclone.service.board;

import js.daangnclone.cmn.DateUtil;
import js.daangnclone.domain.area.Area;
import js.daangnclone.domain.area.AreaRepository;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.board.BoardRepository;
import js.daangnclone.domain.category.Category;
import js.daangnclone.domain.category.CategoryRepository;
import js.daangnclone.web.board.dto.BoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final AreaRepository areaRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Board registerItem(Board board) {
        return boardRepository.save(board);
    }

    @Override
    public List<BoardResponse> inquireAllBoardList() {
        List<Board> findBoardList = boardRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));

        return findBoardList.stream()
                .map(board -> BoardResponse.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .image(board.getImage())
                        .price(board.getPrice())
                        .content(board.getContent())
                        .detail(board.getDetail())
                        .category(categoryRepository.findById(board.getCategory()).get().getCategoryName())
                        .diffCreatedAt(DateUtil.diffDate(board.getCreatedAt()))
                        .nickname(board.getMember().getNickname())
                        .city(areaRepository.findByAreaCd(board.getMember().getCity()).get().getAreaName())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public BoardResponse inquireBoard(Long id) {
        Board findBoard = boardRepository.findById(id).orElse(null);
        Category category = categoryRepository.findById(findBoard.getCategory()).orElse(null);
        Area city = areaRepository.findByAreaCd(findBoard.getMember().getCity()).orElse(null);

        return BoardResponse.builder()
                .id(findBoard.getId())
                .title(findBoard.getTitle())
                .image(findBoard.getImage())
                .price(findBoard.getPrice())
                .content(findBoard.getContent())
                .detail(findBoard.getDetail())
                .category(category.getCategoryName())
                .diffCreatedAt(DateUtil.diffDate(findBoard.getCreatedAt()))
                .nickname(findBoard.getMember().getNickname())
                .city(city.getAreaName())
                .view(findBoard.getView())
                .build();
    }

    @Override
    @Transactional
    public void updateView(Long id) {
        Board findBoard = boardRepository.findById(id).orElse(null);
        findBoard.addView();
    }
}
