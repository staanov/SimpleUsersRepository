import java.util.Set;

public interface UserDAO {
    User getUser(String login);
    Set<User> getAllUsers();
    boolean insertUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(User user);
}
