package application;

public class LoginService {
    private UserDAO userDAO;

    public LoginService() {
        this.userDAO = new UserDAO();
    }

    public boolean login(String username, String password, String role) {
        if (username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            return false;
        }
        return userDAO.validateLogin(username, password, role);
    }
}
