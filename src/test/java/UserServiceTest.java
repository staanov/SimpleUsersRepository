import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private static UserService userService = new UserService();
    private static EmbeddedPostgres embeddedPostgres = null;
    private static SessionFactory sessionFactory = createSessionFactory();

    @BeforeAll
    public static void setUpDb() {
        try {
            embeddedPostgres = EmbeddedPostgres.builder()
                    .setPort(5433)
                    .start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        userService.getUserDao().setCurrentSessionFactory(sessionFactory);

        if (embeddedPostgres != null) {
            TestHelper.executeScript(embeddedPostgres.getPostgresDatabase(), "create_users.sql");
        }
    }

    @AfterAll
    public static void shutdownDb() {
        try {
            embeddedPostgres.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static SessionFactory createSessionFactory() {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .loadProperties("hibernate.properties")
                .build();

        Metadata metadata = new MetadataSources(serviceRegistry)
                .addAnnotatedClass(User.class)
                .buildMetadata();

        return metadata.buildSessionFactory();
    }

    @BeforeEach
    void deleteAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        if (allUsers.size() != 0) {
            for (User user : allUsers) {
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
        assertTrue(allUsers.stream().anyMatch(user -> user.getLogin().equals("Homyak")));
        assertTrue(allUsers.stream().anyMatch(user -> user.getLogin().equals("Antonio")));
        assertTrue(allUsers.stream().anyMatch(user -> user.getLogin().equals("Ohotnitsa")));
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