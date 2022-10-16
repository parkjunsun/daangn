package js.daangnclone.domain.chat;

import js.daangnclone.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findByRoomNum(String roomNum);
    long countByBoard(Board board);

}
