package businessManagement.application;

import businessManagement.domain.User;

import java.io.IOException;
import java.util.List;

public interface UserService {

    List<User> retrieveAll();

    List<User> find(String key);

    void add(User user) throws IOException;

    void signIn(String email,String password);

    void delete(String  email);
}
