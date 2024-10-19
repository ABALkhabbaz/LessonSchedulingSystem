package Actors;

public class User extends Person {
    private String username;
    private String password;

    public User(String name, String phone, int age, String username, String password) {
        super(username, phone, age);
        this.username = username;
        this.password = password;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword(){
        return password;
    }

    // Setters
    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        // Check if the object is the same as the current instance
        if (this == obj) {
            return true;
        }

        // Check if the object is an instance of the User class
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // Cast the object to User and compare the fields
        User user = (User) obj;
        return this.username.equals(user.getUsername()) && this.password.equals(user.getPassword());
    }


}
