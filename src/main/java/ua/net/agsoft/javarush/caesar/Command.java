package ua.net.agsoft.javarush.caesar;

public enum Command {
    ENCRYPT("ENCRYPT"),
    DECRYPT("DECRYPT"),
    BRUTE_FORCE("BRUTE_FORCE");

    private final String code;

    Command(String code) {
        this.code = code;
    }

    public static Command of(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Command code cannot be null or blank.");
        }
        try {
            return Command.valueOf(code.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("BAD command. Possible options: ENCRYPT, DECRYPT or BRUTE_FORCE");
        }
    }

    public String toString() {
        return code;
    }
}
