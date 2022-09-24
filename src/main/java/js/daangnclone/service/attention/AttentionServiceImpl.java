package js.daangnclone.service.attention;

import js.daangnclone.domain.attention.Attention;
import js.daangnclone.domain.attention.AttentionRepository;
import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttentionServiceImpl implements AttentionService{

    private final AttentionRepository attentionRepository;

    @Override
    @Transactional
    public void processAttention(Member member, Board board) {

        Optional<Attention> findAttention = attentionRepository.findByMemberAndBoard(member, board);

        if (findAttention.isPresent()) {
            deleteAttention(member, board);
        } else {
            registerAttention(member, board);
        }
    }

    private void registerAttention(Member member, Board board) {
        Attention attention = new Attention();
        attention.setMember(member);
        attention.setBoard(board);
        attentionRepository.save(attention);
    }

    private void deleteAttention(Member member, Board board) {
        attentionRepository.deleteByMemberAndBoard(member, board);
    }

    @Override
    public String getInpAttentionYn(Member member, Board board) {
        Optional<Attention> findAttention = attentionRepository.findByMemberAndBoard(member, board);
        String attentionInpYn = null;

        if (findAttention.isPresent()) {
            attentionInpYn = "Y";
        } else {
            attentionInpYn = "N";
        }

        return attentionInpYn;
    }

    @Override
    public long countAttentionInBoard(Board board) {
        return attentionRepository.countByBoard(board);
    }
}
