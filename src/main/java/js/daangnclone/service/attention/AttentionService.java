package js.daangnclone.service.attention;

import js.daangnclone.domain.attention.Attention;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;

public interface AttentionService {

    void processAttention(Member member, Board board);
    String getInpAttentionYn(Member member, Board board);
    long countAttentionInBoard(Board board);

}
