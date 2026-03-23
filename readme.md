# I3 Typing Master 2026

Console-based Java typing application for practicing typing speed and accuracy.

## Task list

1)	Welcome menu screen	=> KHORN Victor
2)	User registering screen	=> bunnaka-hash
3)	User reset password screen	=> bunnaka-hash
4)	User login screen		=> bunnaka-hash
5)	List of test results for a user	=> rosajinn
6)	User typing test screen	=> rosajinn
7)	About Us screen		=> rosajinn
8)	README.md (document on how to setup, compile, and run project locally)

## Features

- User registration and login
- Authentication backed by CSV data in [src/database/user.csv](src/database/user.csv)
- Typing Test Level 1 with speed and mistake calculation
- Modular structure (auth, database, typing, util, pages)

## Prerequisites

- Java Development Kit (JDK) 17 or later
- Terminal (PowerShell, Command Prompt, or any shell)

## Project Setup

1. Clone the repository:

```bash
git clone https://github.com/KhornVictor/-i3_typing_master_2026.git
cd -i3_typing_master_2026
```

2. Confirm Java is installed:

```bash
java -version
javac -version
```

## Build and Run

From the project root, run:

```bash
javac -d bin src/App.java src/pages/TypingMasterApp.java src/auth/AuthService.java src/database/model/User.java src/database/service/UserService.java src/typing/TypingTestService.java src/typing/TypingTestResult.java src/util/ConsoleUtil.java
java -cp bin App
```

Or compile all Java files at once with PowerShell:

```powershell
$sources = Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -d bin $sources
java -cp bin App
```

## Data File

- User records are stored in [src/database/user.csv](src/database/user.csv)
- Format:

```csv
username,password
demo,1234
```

- New registered users are automatically written to this file.

## Collaboration

- KHORN Victor: Welcome menu screen
- bunnaka-hash: User registering screen, user reset password screen, user login screen
- rosajinn: List of test results screen, user typing test screen, About Us screen

## Notes

- If [bin](bin) does not exist, create it before compiling:

```bash
mkdir bin
```

- If you use VS Code, you can run [src/App.java](src/App.java) directly with the Java extension.
