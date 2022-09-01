package engine.services.completedquizzeslog;

import engine.entity.CompletedQuizzesLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CompletedQuizzesLogService {


    void save(CompletedQuizzesLog log);

    Page<CompletedQuizzesLog> findAll(String userName, Pageable pageable);


}
