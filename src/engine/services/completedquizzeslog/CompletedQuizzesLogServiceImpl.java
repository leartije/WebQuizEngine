package engine.services.completedquizzeslog;

import engine.entity.CompletedQuizzesLog;
import engine.repository.CompletedQuizzesLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CompletedQuizzesLogServiceImpl implements CompletedQuizzesLogService {

    @Autowired
    private CompletedQuizzesLogRepository repository;

    @Override
    public void save(CompletedQuizzesLog log) {
        repository.save(log);
    }

    @Override
    public Page<CompletedQuizzesLog> findAll(String userName, Pageable pageable) {
        return repository.findByUserName(userName, pageable);
    }
}
