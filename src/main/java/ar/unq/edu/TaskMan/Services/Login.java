package ar.unq.edu.TaskMan.Services;

public class Login {
    private final String userOrEmail;
    private final String password;

    public Login (String userOrEmail, String password){
        this.userOrEmail = userOrEmail;
        this.password = password;
    }

    public String getUserOrEmail() {
        return userOrEmail;
    }

    public String getPassword() {
        return password;
    }

}
