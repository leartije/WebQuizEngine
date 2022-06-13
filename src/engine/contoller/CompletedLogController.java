package engine.contoller;

import engine.entity.CompletedLog;
import engine.services.CompletedLogServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompletedLogController {

    @Autowired
    private CompletedLogServices completedLogServices;


    @GetMapping("/api/quizzes/completed")
    public Page<CompletedLog> findAll(@RequestParam(defaultValue = "0") Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        return completedLogServices.findAll(pageRequest);
    }

}
