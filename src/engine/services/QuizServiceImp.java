package engine.services;

import engine.entity.Answer;
import engine.entity.Quiz;
import engine.entity.Response;
import engine.entity.User;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                quiz.setUser(((CustomUserDetails)currentUser).getUser());
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
            System.out.println(first.get().getAnswer());
            System.out.println(answer.getAnswer());

            int count = 0;
            if (answer.getAnswer() != null && answer.getAnswer() != null) {
                for (int i = 0; i < first.get().getAnswer().size(); i++) {
                    for (int j = 0; j < answer.getAnswer().size(); j++) {
                        if (first.get().getAnswer().get(i).equals(answer.getAnswer().get(j))) {
                            count++;
                        }
                    }
                }
            }

            if ((count == answer.getAnswer().size() && count == first.get().getAnswer().size()) ||
                    (first.get().getAnswer() == null && answer.getAnswer() == null) ||
                    (first.get().getAnswer() == null && answer.getAnswer().size() == 0)) {
                return new Response(true, "Dobro te je");
            }
            return new Response(false, "nevalja");
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @Override
    public void deleteQ(int id) {
        Optional<Quiz> byId = quizRepository.findById(id);
        System.out.println(byId);
        if (byId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Object currentUser = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(byId.get().getUser().getId(), ((CustomUserDetails) currentUser).getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        quizRepository.deleteById(id);
    }
}
