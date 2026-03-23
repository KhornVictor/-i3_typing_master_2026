package database.service;

import database.model.User;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {

	private static final Path USER_CSV_PATH = Paths.get("src", "database", "user.csv");
	private static final String CSV_HEADER = "username,password";
	private final Map<String, User> users = new HashMap<>();

	public UserService() {
		loadUsersFromCsv();
	}

	private void loadUsersFromCsv() {
		ensureCsvExists();
		users.clear();

		try {
			List<String> lines = Files.readAllLines(USER_CSV_PATH);
			for (int i = 1; i < lines.size(); i++) {
				String line = lines.get(i).trim();
				if (line.isEmpty()) {
					continue;
				}

				String[] parts = line.split(",", 2);
				if (parts.length < 2) {
					continue;
				}

				String username = parts[0].trim();
				String password = parts[1].trim();
				if (!username.isEmpty()) {
					users.put(username, new User(username, password));
				}
			}
		} catch (IOException e) {
			System.out.println("Failed to read user.csv: " + e.getMessage());
		}
	}

	private void ensureCsvExists() {
		try {
			if (Files.notExists(USER_CSV_PATH)) {
				Path parent = USER_CSV_PATH.getParent();
				if (parent != null && Files.notExists(parent)) {
					Files.createDirectories(parent);
				}
				Files.write(USER_CSV_PATH, List.of(CSV_HEADER));
			}
		} catch (IOException e) {
			System.out.println("Failed to initialize user.csv: " + e.getMessage());
		}
	}

	private boolean saveUsersToCsv() {
		List<String> lines = new ArrayList<>();
		lines.add(CSV_HEADER);
		for (User user : users.values()) {
			lines.add(user.getUsername() + "," + user.getPassword());
		}

		try {
			Files.write(USER_CSV_PATH, lines);
			return true;
		} catch (IOException e) {
			System.out.println("Failed to write user.csv: " + e.getMessage());
			return false;
		}
	}

	public boolean usernameExists(String username) {
		loadUsersFromCsv();
		String safeUsername = username == null ? "" : username.trim();
		return users.containsKey(safeUsername);
	}

	public boolean createUser(String username, String password) {
		loadUsersFromCsv();
		String safeUsername = username == null ? "" : username.trim();
		if (safeUsername.isEmpty() || password == null || password.isBlank() || users.containsKey(safeUsername)) {
			return false;
		}

		users.put(safeUsername, new User(safeUsername, password));
		return saveUsersToCsv();
	}

	public boolean authenticate(String username, String password) {
		loadUsersFromCsv();
		String safeUsername = username == null ? "" : username.trim();
		User user = users.get(safeUsername);
		return user != null && user.getPassword().equals(password);
	}
}
