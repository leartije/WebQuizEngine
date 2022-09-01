package engine.repository;

import engine.entity.CompletedQuizzesLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompletedQuizzesLogRepository extends JpaRepository<CompletedQuizzesLog, Long>,
        PagingAndSortingRepository<CompletedQuizzesLog, Long> {

    Page<CompletedQuizzesLog> findByUserName(String userName, Pageable pageable);

}
