import java.util.List;

public class UserService {
    private static UserDao userDao;

    public UserService() {
        userDao = new UserDao();
    }

    public User getUser(String login) {
        userDao.openCurrentSession();
        User user = userDao.getUser(login);
        userDao.closeCurrentSession();
        return user;
    }

    public List<User> getAllUsers() {
        userDao.openCurrentSession();
        List<User> users = userDao.getAllUsers();
        userDao.closeCurrentSession();
        return users;
    }

    public void insertUser(User user) {
        userDao.openCurrentSessionWithTransaction();
        userDao.insertUser(user);
        userDao.closeCurrentSessionWithTransaction();
    }

    public void updateUser(User user) {
        userDao.openCurrentSessionWithTransaction();
        userDao.updateUser(user);
        userDao.closeCurrentSessionWithTransaction();
    }

    public void deleteUser(String login) {
        userDao.openCurrentSessionWithTransaction();
        User user = userDao.getUser(login);
        userDao.deleteUser(user);
        userDao.closeCurrentSessionWithTransaction();
    }

    public UserDao userDao() {
        return userDao;
    }
}
