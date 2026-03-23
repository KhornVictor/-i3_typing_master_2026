package typing;

public class TypingTestService {

    private static final String LEVEL_1_TEXT = "ffff iiii dddd kkkk ssss llll aaaa ;;;; fdsa jkl; fdsa jkl; asdf jkl; asdf jkl; "
            + "sad dad had lad ask all fall hall flask shall gash hash flash glass glad flag sag hash lash. "
            + "Ask a glad lad all a salad.";

    public String getLevel1Text() {
        return LEVEL_1_TEXT;
    }

    public TypingTestResult evaluateLevel1(String typedText, long elapsedMillis) {
        String safeTypedText = typedText == null ? "" : typedText;
        long safeElapsedMillis = Math.max(1L, elapsedMillis);

        int totalInputChars = safeTypedText.length();
        int mistakes = countMistakes(LEVEL_1_TEXT, safeTypedText);
        int words = safeTypedText.isBlank() ? 0 : safeTypedText.trim().split("\\s+").length;
        double minutes = safeElapsedMillis / 60000.0;

        int speedCharsPerMin = (int) Math.round(totalInputChars / minutes);
        int speedWordsPerMin = (int) Math.round(words / minutes);

        return new TypingTestResult(speedCharsPerMin, speedWordsPerMin, totalInputChars, mistakes);
    }

    private int countMistakes(String expected, String actual) {
        int minLength = Math.min(expected.length(), actual.length());
        int mistakes = Math.abs(expected.length() - actual.length());

        for (int i = 0; i < minLength; i++) {
            if (expected.charAt(i) != actual.charAt(i)) {
                mistakes++;
            }
        }

        return mistakes;
    }
}
