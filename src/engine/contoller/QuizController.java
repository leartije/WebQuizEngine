package engine.contoller;

import engine.entity.Answer;
import engine.entity.Quiz;
import engine.entity.Response;
import engine.entity.User;
import engine.services.QuizService;
import engine.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class QuizController {

    @Autowired
    private QuizService quizService;
    @Autowired
    private UserService userService;


    @PostMapping("/api/quizzes")
    public Quiz sentQuiz(@Valid @RequestBody Quiz quiz) {
        return quizService.saveQuiz(quiz);
    }

    @GetMapping("/api/quizzes")
    public List<Quiz> getAllQuestions() {
        return quizService.printAllQuizzes();
    }

    @GetMapping("/api/quizzes/{id}")
    public Quiz getById(@PathVariable("id") int id) {
        return quizService.getQuizById(id);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Response getResponse(@PathVariable("id") int id, @RequestBody Answer answer) {
        return quizService.getAnswer(id, answer);
    }

    @PostMapping("/api/register")
    public void registerUser(@Valid @RequestBody User user) {
        userService.saveUser(user);
    }

    @DeleteMapping("/api/quizzes/{id}")
    public void deleteQ(@PathVariable("id") int id) {
        quizService.deleteQ(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

}
