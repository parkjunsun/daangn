package js.daangnclone.service.board;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.board.BoardStatus;
import js.daangnclone.domain.member.Member;
import js.daangnclone.web.board.dto.BoardForm;
import js.daangnclone.web.board.dto.BoardMultiResponse;
import js.daangnclone.web.board.dto.BoardSingleResponse;
import js.daangnclone.web.purchase.dto.PurchaseResponse;
import js.daangnclone.web.sale.dto.SaleResponse;

import java.util.List;

public interface BoardService {

    Board registerItem(BoardForm boardForm, Member member);
    List<BoardMultiResponse> inquireAllBoardList();
    BoardSingleResponse inquireBoard(Long id);
    void updateView(Long id);
    Board updateBoardStatus(Long id, BoardStatus boardStatus);
    Board findBoard(Long id);
    List<SaleResponse> inquireSaleList(Member member, BoardStatus boardStatus);
    SaleResponse inquireSale(Long boardId);
    long getCount(Member member, BoardStatus boardStatus);
    void updatePurchaser(Long boardId, Member purchaser);
    List<PurchaseResponse> inquirePurchaseList(Member seller);


}
