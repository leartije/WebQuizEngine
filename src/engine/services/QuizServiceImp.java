package engine.services;

import engine.entity.Answer;
import engine.entity.Quiz;
import engine.entity.Response;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuizServiceImp implements QuizService {

    @Autowired
    private QuizRepository quizRepository;


    @Override
    public Quiz saveQuiz(Quiz quiz) {
        Object currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentUser instanceof UserDetails) {
            if (quiz != null) {
                quiz.setUser(((CustomUserDetails) currentUser).getUser());
                return quizRepository.save(quiz);
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @Override
    public List<Quiz> printAllQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    public Quiz getQuizById(Integer quizId) {
        Optional<Quiz> byId = quizRepository.findById(quizId);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Override
    public Response getAnswer(int id, Answer answer) {
        Optional<Quiz> first = printAllQuizzes().stream()
                .filter(quiz -> quiz.getId().equals(id))
                .findFirst();

        if (first.isPresent()) {
            List<Integer> correctAnswers = first.get().getAnswer();
            List<Integer> userAnswers = answer.getAnswer();

//           boolean isEqual = correctAnswers.equals(userAnswers); zasto ovo ne radi?

            int count = isEqual(correctAnswers, userAnswers);

            if ((count == correctAnswers.size() && count == userAnswers.size()) ||
                    (first.get().getAnswer() == null && answer.getAnswer() == null) ||
                    (first.get().getAnswer() == null && answer.getAnswer().size() == 0)) {

                return new Response(true, "Congratulations, you're right!");
            }
            return new Response(false, "Wrong answer! Please, try again.");
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Override
    public void deleteQ(int id) {
        Optional<Quiz> quizForDel = quizRepository.findById(id);

        if (quizForDel.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Object currentLogInUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(quizForDel.get().getUser().getId(), ((CustomUserDetails) currentLogInUser).getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        quizRepository.deleteById(id);
    }

    @Override
    public List<Quiz> getAllQuizzes(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Quiz> pagedResult = quizRepository.findAll(paging);

        if (pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<>();
        }
    }

    private int isEqual(List<Integer> correctAnswers, List<Integer> userAnswers) {
        int count = 0;
        if (correctAnswers != null && userAnswers != null) {
            for (Integer correctAnswer : correctAnswers) {
                for (Integer userAnswer : userAnswers) {
                    if (correctAnswer.equals(userAnswer)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    @Override
    public Page<Quiz> findAll(Pageable pageable) {
        return quizRepository.findAll(pageable);
    }
}
