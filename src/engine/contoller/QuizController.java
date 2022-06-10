package engine.contoller;

import engine.entity.Answer;
import engine.entity.Quiz;
import engine.entity.Response;
import engine.entity.User;
import engine.repository.QuizRepository;
import engine.services.QuizService;
import engine.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class QuizController {

    @Autowired
    private QuizService quizService;



    @PostMapping("/api/quizzes")
    public Quiz sentQuiz(@Valid @RequestBody Quiz quiz) {
        return quizService.saveQuiz(quiz);
    }

//    @GetMapping("/api/quizzes")
//    public List<Quiz> getAllQuestions() {
//        return quizService.printAllQuizzes();
//    }

    @GetMapping("/api/quizzes")
    public ResponseEntity<List<Quiz>> getAllQuizzes(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy) {
        List<Quiz> quizList = quizService.getAllQuizzes(page, pageSize, sortBy);

        return new ResponseEntity<>(quizList, new HttpHeaders(), HttpStatus.OK);
    }


    @GetMapping("/api/quizzes/{id}")
    public Quiz getById(@PathVariable("id") int id) {
        return quizService.getQuizById(id);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Response getResponse(@PathVariable("id") int id, @RequestBody Answer answer) {
        return quizService.getAnswer(id, answer);
    }

    @DeleteMapping("/api/quizzes/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteQ(@PathVariable("id") int id) {
        quizService.deleteQ(id);
    }

    @GetMapping("/api/test")
    @ResponseBody
    public String test(Authentication authentication) {
        return authentication.getName();
    }

}
