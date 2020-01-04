import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.Set;

public class UserService implements UserDAO, Observable {
    @Override
    public User getUser(String login) {
        return null;
    }

    @Override
    public Set<User> getAllUsers() {
        return null;
    }

    @Override
    public boolean insertUser(User user) {
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        return false;
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {

    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {

    }
}
