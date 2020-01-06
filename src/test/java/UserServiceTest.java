import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private static UserService userService = new UserService();

    @BeforeEach
    void deleteAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        if (allUsers.size() != 0) {
            for (User user: allUsers) {
                userService.deleteUser(user.getLogin());
            }
        }
    }

    @Test
    void getUser() {
        User testUser = new User("Antonio", "fk399kKS", "Anton", "Ivanov");
        userService.insertUser(testUser);
        assertEquals(new User("Antonio", "fk399kKS", "Anton", "Ivanov"),
                userService.getUser("Antonio"));
    }

    @Test
    void getAllUsers() {
        User user1 = new User("Homyak", "jeijDW29", "Petr", "Khomyakov");
        User user2 = new User("Antonio", "fk399kKS", "Anton", "Ivanov");
        User user3 = new User("Ohotnitsa", "KFWJK202kf2", "Larisa", "Petrova");
        userService.insertUser(user1);
        userService.insertUser(user2);
        userService.insertUser(user3);
        List<User> allUsers = userService.getAllUsers();
        assertEquals(3, allUsers.size());
        assertTrue(allUsers.stream().anyMatch(user->user.getLogin().equals("Homyak")));
    }

    @Test
    void insertUser() {
        User testUser = new User("Antonio", "fk399kKS", "Anton", "Ivanov");
        userService.insertUser(testUser);
        assertEquals(List.of(testUser), userService.getAllUsers());
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
        User testUser = new User("Antonio", "fk399kKS", "Anton", "Ivanov");
        userService.insertUser(testUser);
        userService.deleteUser(testUser.getLogin());
        Optional<User> optionalUser = Optional.ofNullable(userService.getUser(testUser.getLogin()));
        assertFalse(optionalUser.isPresent());
    }

    @Test
    void userDao() {
    }
}