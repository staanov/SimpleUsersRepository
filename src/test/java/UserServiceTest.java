import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.PersistenceException;
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
    void getExistingUserShouldReturnUser() {
        User testUser = new User("Antonio", "fk399kKS", "Anton", "Ivanov");
        userService.insertUser(testUser);
        Optional<User> extractedUser = Optional.ofNullable(userService.getUser("Antonio"));
        assertTrue(extractedUser.isPresent());
        assertEquals(testUser, extractedUser.get());
    }

    @Test
    void getUserWhoDoesNotExistShouldReturnEmptyRow() {
        assertFalse(Optional.ofNullable(userService.getUser("Antonio")).isPresent());
    }

    @Test
    void getAllUsersShouldReturnListOfThreeInsertedUsers() {
        User user1 = new User("Homyak", "jeijDW29", "Petr", "Khomyakov");
        User user2 = new User("Antonio", "fk399kKS", "Anton", "Ivanov");
        User user3 = new User("Ohotnitsa", "KFWJK202kf2", "Larisa", "Petrova");
        userService.insertUser(user1);
        userService.insertUser(user2);
        userService.insertUser(user3);
        List<User> allUsers = userService.getAllUsers();
        assertEquals(3, allUsers.size());
        assertTrue(allUsers.stream().anyMatch(user->user.getLogin().equals("Homyak")));
        assertTrue(allUsers.stream().anyMatch(user->user.getLogin().equals("Antonio")));
        assertTrue(allUsers.stream().anyMatch(user->user.getLogin().equals("Ohotnitsa")));
    }

    @Test
    void insertUserShouldInsertRowInDatabase() {
        User testUser = new User("Antonio", "fk399kKS", "Anton", "Ivanov");
        userService.insertUser(testUser);
        assertEquals(List.of(testUser), userService.getAllUsers());
    }

    @Test
    void updatePasswordOfUserShouldSucceed() {
        User testUser = new User("Antonio", "fk399kKS", "Anton", "Ivanov");
        userService.insertUser(testUser);
        testUser.setPassword("fji3FWk");
        userService.updateUser(testUser);
        assertEquals("fji3FWk", userService.getUser("Antonio").getPassword());
    }

    @Test
    void updateFirstNameOfUserShouldSucceed() {
        User testUser = new User("Antonio", "fk399kKS", "Anton", "Ivanov");
        userService.insertUser(testUser);
        testUser.setFirstName("Ivan");
        userService.updateUser(testUser);
        assertEquals("Ivan", userService.getUser("Antonio").getFirstName());
    }

    @Test
    void updateLastNameOfUserShouldSucceed() {
        User testUser = new User("Antonio", "fk399kKS", "Anton", "Ivanov");
        userService.insertUser(testUser);
        testUser.setLastName("Petrov");
        userService.updateUser(testUser);
        assertEquals("Petrov", userService.getUser("Antonio").getLastName());
    }

    @Test
    void deleteUserShouldDeleteUserFromDatabase() {
        User testUser = new User("Antonio", "fk399kKS", "Anton", "Ivanov");
        userService.insertUser(testUser);
        userService.deleteUser(testUser.getLogin());
        Optional<User> optionalUser = Optional.ofNullable(userService.getUser(testUser.getLogin()));
        assertFalse(optionalUser.isPresent());
    }

    @Test
    void insertOfExistingUserShouldBePrevented() {
        User testUser = new User("Antonio", "fk399kKS", "Anton", "Ivanov");
        userService.insertUser(testUser);
        assertThrows(PersistenceException.class, () -> userService.insertUser(testUser));
    }
}