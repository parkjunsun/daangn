package js.daangnclone.domain.attention;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttentionRepository extends JpaRepository<Attention, Long> {

    Optional<Attention> findByMemberAndBoard(Member member, Board board);
    void deleteByMemberAndBoard(Member member, Board board);
    long countByBoard(Board board);
    List<Attention> findByMember(Member member);
}
