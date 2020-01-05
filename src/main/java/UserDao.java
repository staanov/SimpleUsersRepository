import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.imageio.spi.ServiceRegistry;
import java.lang.module.Configuration;
import java.util.List;

public class UserDao implements UserDaoInterface<User, String> {
    private Session currentSession;
    private Transaction currentTransaction;

    public UserDao() {
    }

    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }

    public Session openCurrentSessionWithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void closeCurrentSessionWithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    private static SessionFactory getSessionFactory() {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        MetadataSources metadataSources = new MetadataSources(serviceRegistry).addAnnotatedClass(User.class);
        Metadata metadata = metadataSources.getMetadataBuilder().build();
        return metadata.getSessionFactoryBuilder().build();
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    @Override
    public User getUser(String s) {
        return getCurrentSession().get(User.class, s);
    }

    @Override
    public List<User> getAllUsers() {
        return getCurrentSession().createQuery("FROM User").list();
    }

    @Override
    public void insertUser(User user) {
        getCurrentSession().save(user);
    }

    @Override
    public void updateUser(User user) {
        getCurrentSession().update(user);
    }

    @Override
    public void deleteUser(User user) {
        getCurrentSession().delete(user);
    }
}
