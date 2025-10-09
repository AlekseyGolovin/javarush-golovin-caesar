package ua.net.agsoft.javarush.caesar;

public enum Command {
    BAD_COMMAND("BAD_COMMAND"),
    ENCRYPT("ENCRYPT"),
    DECRYPT("DECRYPT"),
    BRUTE_FORCE("BRUTE_FORCE");

    private final String code;

    Command(String code) {
        this.code = code;
    }

    public static Command of(String code) {
        for (Command command : Command.values()) {
            if (command.code.equals(code)) {
                return command;
            }
        }
        return Command.BAD_COMMAND;
    }

    public String toString() {
        return code;
    }
}
