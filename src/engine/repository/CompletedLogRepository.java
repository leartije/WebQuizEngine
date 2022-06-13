package engine.repository;

import engine.entity.CompletedLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface CompletedLogRepository extends JpaRepository<CompletedLog, Long>,
        PagingAndSortingRepository<CompletedLog, Long> {


}
