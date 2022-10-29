package js.daangnclone.domain.chat;

import js.daangnclone.domain.board.Board;
import js.daangnclone.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByRoomNum(String roomNum);
    long countByBoard(Board board);

    @Query("select c from Chat c where c.seller = :member or c.buyer = :member order by c.createdAt desc")
    List<Chat> findBySellerOrBuyer(@Param("member") Member member);

    List<Chat> findByBoard(Board board);

}
