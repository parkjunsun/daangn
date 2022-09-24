package js.daangnclone.domain.board;

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

//    @EntityGraph(attributePaths = {"member"})
//    Optional<Board> findBoard(Long id);

    @Query("select distinct b from Board b left join fetch b.commentList where b.id = :id")
    Optional<Board> findBoard(@Param("id") Long id);

}
