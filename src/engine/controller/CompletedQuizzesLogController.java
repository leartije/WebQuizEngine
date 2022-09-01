package engine.controller;

import engine.entity.CompletedQuizzesLog;
import engine.services.completedquizzeslog.CompletedQuizzesLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompletedQuizzesLogController {

    @Autowired
    private CompletedQuizzesLogService logService;


    @GetMapping("/api/quizzes/completed")
    public Page<CompletedQuizzesLog> findAll(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "completedAt") String sortBy,
            Authentication auth
    ) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sortBy));
        return logService.findAll(auth.getName(), pageable);
    }

}
