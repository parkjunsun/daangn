package js.daangnclone.domain.chat;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ChatRepository extends ReactiveMongoRepository<Chat, String> {

    @Tailable // 커서를 안닫고 계속 유지한다.
    @Query("{boardId:?0, senderId:?1}")
    Flux<Chat> findByBoardIdAndSenderId(Long boardId, Long senderId); //Flux(흐름) response 를 유지하면서 데이터를 계속 흘려보내기

    @Tailable
    @Query("{roomNum: ?0}")
    Flux<Chat> mFindByRoomNum(String roomNum);

}
