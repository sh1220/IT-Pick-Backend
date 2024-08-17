package store.itpick.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import store.itpick.backend.model.Debate;
import store.itpick.backend.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface DebateRepository extends JpaRepository<Debate, Long> {
    @Query("SELECT d FROM Debate d WHERE d.keyword.keywordId = :keywordId ORDER BY d.hits DESC")
    List<Debate> findByKeywordIdOrderByHitsDesc(Long keywordId);


    @Query("SELECT d FROM Debate d WHERE d.keyword.keywordId = :keywordId ORDER BY d.createAt DESC")
    List<Debate> findByKeywordIdOrderByCreateAtDesc(Long keywordId);

    List<Debate> getDebateByUser(User user);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.debate = :debate")
    Long countCommentsByDebate(@Param("debate") Debate debate);

    @Query("SELECT DISTINCT d FROM Debate d " +
            "JOIN d.vote v " +
            "JOIN v.voteOptions vo " +
            "JOIN vo.userVoteChoices uvc " +
            "WHERE uvc.user = :user")
    List<Debate> findDebatesByUserVoteChoice(@Param("user") User user);

}
