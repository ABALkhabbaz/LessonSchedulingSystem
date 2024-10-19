public class User extends Person {
    private String username;
    private String password;


    public User(String name, String phone, int age, String username, String password) {
        super(username, phone, age);
        this.username = username;
        this.password = password;
    }

}
