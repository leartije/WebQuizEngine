package engine.services.webquiz;

import engine.entity.Answer;
import engine.entity.Quiz;
import engine.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WebQuizService {
    List<Quiz> printAll();

    Page<Quiz> findAllQuizzes(Pageable pageable);

    void saveQuestion(Quiz quiz, String userName);

    Quiz getQuestion(Long id);

    Response getResponse(Long id, Answer answer, String userName);

    void delete(Long id, String userName);

    List<Quiz> getAllByCurrentUser(String userName);

}
