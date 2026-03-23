package function;

import java.io.IOException;

public class ClearScreen {
    public void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error clearing screen: " + e.getMessage());
        }
    }

    public void systemPause() {
        try {
            new ProcessBuilder("cmd", "/c", "pause").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error pausing system: " + e.getMessage());
        }
    }
}
