package js.daangnclone.domain.review;

import js.daangnclone.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByReceiver(Member receiver);
    long countByReceiver(Member receiver);
    long countByReceiverAndReviewType(Member receiver, ReviewType reviewType);
}
