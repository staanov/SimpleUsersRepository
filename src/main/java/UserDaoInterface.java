import java.util.List;

public interface UserDaoInterface<T, Id> {
    T getUser(Id id);
    List<T> getAllUsers();
    void insertUser(T entity);
    void updateUser(T entity);
    void deleteUser(T entity);
}
