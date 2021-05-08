package businessManagement.application;

import businessManagement.application.exception.*;
import businessManagement.config.EmailConfirmed;
import businessManagement.config.GoogleConfirmed;
import businessManagement.config.PasswordValidator;
import businessManagement.domain.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    PasswordValidator passwordValidator = new PasswordValidator();
    EmailConfirmed emailConfirmed = new EmailConfirmed();
    GoogleConfirmed googleConfirmed=new GoogleConfirmed();
    private final Path TEMP_URL = Path.of("temp/");
    private final Path USER_URL = Path.of("temp/business.txt");

    public UserServiceImpl() {

        if (Files.notExists(TEMP_URL)) {
            try {
                Files.createDirectories(TEMP_URL);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> retrieveAll() {
        try {
            List<User> users = new ArrayList<>();
            Scanner scanner = new Scanner(new File(USER_URL.toString()));
            while (scanner.hasNextLine()) {
                String[] properties = scanner.nextLine().split(",");

                User user = new User();
                user.setName(properties[0]);
                user.setSurname(properties[1]);
                user.setEmail(properties[2]);
                user.setPassword(properties[3]);

                users.add(user);
            }

            return users;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> find(String key) {
        return retrieveAll()
                .stream()
                .filter(e->e.getName().contains(key)||
                        e.getSurname().contains(key)||
                        e.getEmail().contains(key))
                .collect(Collectors.toList());
    }

    @Override
    public void add(User user) {
        try {

            List<User> users = retrieveAll();
            Optional<User> userOptional = users.
                    stream()
                    .filter(user1 -> user1.getEmail().equals(user.getEmail()))
                    .findAny();

            Boolean password = passwordValidator.isValid(user.getPassword());
            Boolean email = emailConfirmed.confirmed(user.getEmail());

            if (!password) {
                throw new PasswordNotValidException("Password not a Valid!");
            } else if (!email) {
                throw new EmailNotConfirmedException("Email not a Valid!");
            } else if (!userOptional.isPresent()) {
                FileWriter fileWriter = new FileWriter(USER_URL.toString(), true);
                fileWriter.write(user.toString());
                fileWriter.write("\n");
                fileWriter.close();
                googleConfirmed.GoogleConfirm();
            } else {
                throw new EmailIsNotEnuqieException("Email is unique!");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void signIn(String email, String password) {
          retrieveAll()
                  .stream()
                  .filter(e->e.getEmail().equals(email)&&e.getPassword().equals(password))
                  .findAny()
                  .orElseThrow(()->  new EmailOrPasswordIncorrectException("Email or password incorrect!"));
    }

    @Override
    public void delete(String email) {
        List<User> users=retrieveAll();
        Optional<User>user=users
                .stream()
                .filter(user1 -> user1.getEmail().equals(email))
                .findAny();

        if (!user.isPresent()){
            throw new UserNotFoundException("USer Not Found!");
        }

        users.remove(user.get());
        try {
            FileWriter fileWriter = new FileWriter(USER_URL.toString());
            for (User stud : users) {
                fileWriter.write(stud.toString());
                fileWriter.write('\n');
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new UserCannotAddToFIleException(e.getMessage());
        }

    }
}
