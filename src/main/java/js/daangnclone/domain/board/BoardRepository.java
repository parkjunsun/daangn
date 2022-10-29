package js.daangnclone.domain.board;

import js.daangnclone.domain.member.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    @Override
    @EntityGraph(attributePaths = {"member"})
    List<Board> findAll(Sort sort);

    @Query("select b from Board b where b.id = :id")
    Optional<Board> findBoard(@Param("id") Long id);

    List<Board> findByMemberAndBoardStatus(Member member, BoardStatus boardStatus);

    long countByMemberAndBoardStatus(Member member, BoardStatus boardStatus);

    List<Board> findByPurchaser(Member purchaser);

}
