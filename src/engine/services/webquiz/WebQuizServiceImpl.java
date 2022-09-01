package engine.services.webquiz;

import engine.entity.Answer;
import engine.entity.CompletedQuizzesLog;
import engine.entity.Quiz;
import engine.entity.Response;
import engine.repository.QuizRepository;
import engine.services.completedquizzeslog.CompletedQuizzesLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WebQuizServiceImpl implements WebQuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CompletedQuizzesLogService logService;

    @Override
    public List<Quiz> printAll() {
        return quizRepository.findAll();
    }

    @Override
    public Page<Quiz> findAllQuizzes(Pageable pageable) {
        return quizRepository.findAll(pageable);
    }

    @Override
    public void saveQuestion(Quiz quiz, String userName) {
        quiz.setUserName(userName);
        quizRepository.save(quiz);
    }

    @Override
    public Quiz getQuestion(Long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return quiz.get();
    }

    @Override
    public Response getResponse(Long id, Answer answer, String userName) {
        if (checkAnswers(id, answer.getAnswer())) {

            Quiz quiz = getQuestion(id);
            CompletedQuizzesLog log = CompletedQuizzesLog.builder()
                    .id(quiz.getId())
                    .userName(userName)
                    .completedAt(LocalDateTime.now())
                    .build();

            logService.save(log);

            return new Response(true, "Congratulations, you're right!");
        }
        return new Response(false, "Wrong answer! Please, try again.");
    }

    @Override
    public List<Quiz> getAllByCurrentUser(String userName) {
        if (userName == null) {
            throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED);
        }
        return quizRepository.findByUserName(userName);
    }

    @Override
    public void delete(Long id, String userName) {
        Quiz quiz = getQuestion(id);

        if (!Objects.equals(userName, quiz.getUserName())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        quizRepository.deleteById(id);
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    private boolean checkAnswers(Long id, int[] answers) {
        Quiz quiz = getQuestion(id);
        return Arrays.equals(quiz.getAnswer(), answers);
    }


}
