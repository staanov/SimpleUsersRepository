public class App {
    public static void main(String[] args) {
        UserService userService = new UserService();
        User user1 = new User("Homyak", "jeijDW29", "Petr", "Khomyakov");
        User user2 = new User("Antonio", "fk399kKS", "Anton", "Ivanov");
        User user3 = new User("Ohotnitsa", "KFWJK202kf2", "Larisa", "Petrova");
        userService.insertUser(user1);
        userService.insertUser(user2);
        userService.insertUser(user3);
    }
}
