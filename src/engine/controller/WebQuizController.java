package engine.controller;

import engine.entity.Answer;
import engine.entity.Quiz;
import engine.entity.Response;
import engine.services.webquiz.WebQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
public class WebQuizController {
    @Autowired
    private WebQuizService webQuizService;


    //GET

    @GetMapping("/api/quizzes")
    public Page<Quiz> findAll(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return webQuizService.findAllQuizzes(pageable);
    }

    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable Long id) {
        return ResponseEntity.ok(webQuizService.getQuestion(id));
    }

    @GetMapping("/api/test")
    public List<Quiz> getAll(Authentication auth) {
        return webQuizService.getAllByCurrentUser(auth.getName());
    }

    //POST

    @PostMapping("/api/quizzes")
    public Quiz postQuiz(@Valid @RequestBody Quiz quiz, Authentication auth) {
        webQuizService.saveQuestion(quiz, auth.getName());
        return quiz;
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Response answer(@PathVariable Long id, @RequestBody Answer answer, Authentication auth) {
        return webQuizService.getResponse(id, answer, auth.getName());
    }

    //DELETE

    @DeleteMapping("/api/quizzes/{id}")
    public void delete(@PathVariable Long id, Authentication auth) {
        webQuizService.delete(id, auth.getName());
    }
}


