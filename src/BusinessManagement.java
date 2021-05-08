import businessManagement.config.EmailConfirmed;
import businessManagement.application.UserService;
import businessManagement.application.UserServiceImpl;
import businessManagement.domain.User;

import java.io.IOException;
import java.util.List;

public class BusinessManagement {
    public static void main(String[] args) throws IOException {
        UserService userService= new UserServiceImpl();
        User user=new User();
        user.setName("Ferid");
        user.setSurname("Quluzade");
        user.setEmail("quluzadef14@gmail.com");
        user.setPassword("Hello world$123");

        List<User> users=userService.retrieveAll();
//        for (User user1:
//             users) {
//            System.out.println(user1);
//        }
//        userService.add(user);
        userService.delete("quluzadef14@gmail.com");
    }
}
