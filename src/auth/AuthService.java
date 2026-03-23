package auth;

import database.service.UserService;

public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public enum RegistrationStatus {
        SUCCESS,
        CANCELLED,
        USERNAME_EXISTS,
        PASSWORD_EMPTY,
        PASSWORD_MISMATCH
    }

    public enum LoginStatus {
        SUCCESS,
        CANCELLED,
        INVALID_CREDENTIALS
    }

    public RegistrationStatus register(String username, String password, String confirmPassword) {
        String safeUsername = username == null ? "" : username.trim();

        if (safeUsername.isEmpty()) {
            return RegistrationStatus.CANCELLED;
        }

        if (userService.usernameExists(safeUsername)) {
            return RegistrationStatus.USERNAME_EXISTS;
        }

        if (password == null || password.isBlank()) {
            return RegistrationStatus.PASSWORD_EMPTY;
        }

        if (!password.equals(confirmPassword)) {
            return RegistrationStatus.PASSWORD_MISMATCH;
        }

        userService.createUser(safeUsername, password);
        return RegistrationStatus.SUCCESS;
    }

    public LoginStatus login(String username, String password) {
        String safeUsername = username == null ? "" : username.trim();

        if (safeUsername.isEmpty()) {
            return LoginStatus.CANCELLED;
        }

        if (userService.authenticate(safeUsername, password)) {
            return LoginStatus.SUCCESS;
        }

        return LoginStatus.INVALID_CREDENTIALS;
    }
}
