package typing;

public class TypingTestResult {

    private final int speedCharsPerMin;
    private final int speedWordsPerMin;
    private final int totalInputChars;
    private final int totalMistakes;

    public TypingTestResult(int speedCharsPerMin, int speedWordsPerMin, int totalInputChars, int totalMistakes) {
        this.speedCharsPerMin = speedCharsPerMin;
        this.speedWordsPerMin = speedWordsPerMin;
        this.totalInputChars = totalInputChars;
        this.totalMistakes = totalMistakes;
    }

    public int getSpeedCharsPerMin() {
        return speedCharsPerMin;
    }

    public int getSpeedWordsPerMin() {
        return speedWordsPerMin;
    }

    public int getTotalInputChars() {
        return totalInputChars;
    }

    public int getTotalMistakes() {
        return totalMistakes;
    }
}
