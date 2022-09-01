package engine.services.registeruser;

import engine.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface RegisterUserService {

    void save(User user);

}
