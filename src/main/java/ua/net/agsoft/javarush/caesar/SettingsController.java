package ua.net.agsoft.javarush.caesar;

import ua.net.agsoft.javarush.caesar.util.Util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class SettingsController {

    public static final String MANUAL_REQUEST = "Unrecognized launch arguments. Enter parameters manually? [Y/N]";
    public static final String PATH_REQUEST = "Please provide the absolute path to the file";
    public static final String KEY_REQUEST = "Enter the cipher key (the integer number of places to shift in the alphabet)";
    public static final String COMMAND_REQUEST = """
            Please specify the operation you wish to perform:
            1 - ENCRYPT
            2 - DECRYPT
            3 - BRUTE FORCE""";
    public static final String KEY_WORD_REQUEST = """
            Please enter the full path to the dictionary file,
            or leave the line blank and press Enter to skip""";
    public static final String ALPHABET_REQUEST = """
            Please enter the alphabet string to be used for encryption/decryption:
            1 - EN_BASIC - Basic Latin Alphabet. Only lowercase and uppercase Latin letters.
            2 - EN_ADVANCED - Extended Latin Alphabet. Basic Latin alphabet plus common punctuation and space.
            3 - LATIN - Full Latin Set. Includes digits, Latin letters, and a complete set of ASCII special characters.
            4 - CYRIL - Full Cyrillic Set. Includes everything from LATIN plus all Cyrillic letters
            5 - LATIN_MIX - Mixed Latin Key. Same characters as LATIN, but randomly shuffled.
            6 - CYRIL_MIX - Mixed Cyrillic Key: Same characters as CYRIL, but randomly shuffled.""";

    public RunSettings getSettingsFromArguments(String[] args) {
        RunSettings runSettings = new RunSettings();
        configureSettings(args, runSettings);
        return runSettings;
    }

    private void configureSettings(String[] args, RunSettings runSettings) {
        if (args.length < 2 || args.length > 4) {
            return;
        }
        try {
            runSettings.setCommand(Command.of(args[0]));
            runSettings.setFilePath(Path.of(args[1]));
            if (args.length >= 3) {
                runSettings.setKey(args[2]);
                runSettings.setExternalWordlist(args[2]);
            }
            if (args.length == 4) {
                runSettings.setAlphabetType(args[3]);
            }
        } catch (Exception ignore) {
        }
    }

    public RunSettings getManualSettings() {
        RunSettings runSettings = new RunSettings();
        if (!requestManualInput()) {
            return runSettings;
        }
        Command command = requestCommand();
        runSettings.setCommand(command);
        runSettings.setFilePath(requestFilePath());
        switch (command) {
            case DECRYPT, ENCRYPT -> runSettings.setKey(requestKeyOffset());
            case BRUTE_FORCE -> runSettings.setExternalWordlist(requestKeyWordFile());
        }
        String alphabetType = requestAlphabetType();
        runSettings.setAlphabetType(alphabetType);
        return runSettings;
    }

    private boolean requestManualInput() {
        Scanner scanner = new Scanner(System.in);
        boolean isManual = false;
        boolean isCorrect;
        do {
            System.out.println(MANUAL_REQUEST);
            String answer = scanner.nextLine().toLowerCase();
            switch (answer) {
                case "y", "yes" -> {
                    isCorrect = true;
                    isManual = true;
                }
                case "n", "no" -> isCorrect = true;
                default -> isCorrect = false;
            }
        } while (!isCorrect);
        return isManual;
    }

    private Command requestCommand() {
        Scanner scanner = new Scanner(System.in);
        Command command = null;
        do {
            System.out.println(COMMAND_REQUEST);
            String answer = scanner.nextLine();
            if (Util.isInteger(answer)) {
                int userChoice = Util.tryToInt(answer);
                switch (userChoice) {
                    case 1 -> command = Command.ENCRYPT;
                    case 2 -> command = Command.DECRYPT;
                    case 3 -> command = Command.BRUTE_FORCE;
                    default -> {
                    }
                }
            }
        } while (command == null);
        return command;
    }

    private Path requestFilePath() {
        Scanner scanner = new Scanner(System.in);
        Path path;
        do {
            System.out.println(PATH_REQUEST);
            String answer = scanner.nextLine();
            path = Path.of(answer);
        } while (!Files.isRegularFile(path));
        return path;
    }

    private String requestKeyOffset() {
        Scanner scanner = new Scanner(System.in);
        String key;
        do {
            System.out.println(KEY_REQUEST);
            key = scanner.nextLine();
        } while (!Util.isInteger(key));
        return key;
    }

    private String requestKeyWordFile() {
        Scanner scanner = new Scanner(System.in);
        Path path;
        do {
            System.out.println(KEY_WORD_REQUEST);
            String answer = scanner.nextLine();
            if (answer.isBlank()) {
                return "";
            }
            path = Path.of(answer);
        } while (!Files.isRegularFile(path));
        return path.toString();
    }

    private String requestAlphabetType() {
        Scanner scanner = new Scanner(System.in);
        String alphabet = "";
        do {
            System.out.println(ALPHABET_REQUEST);
            String answer = scanner.nextLine();
            if (Util.isInteger(answer)) {
                int userChoice = Util.tryToInt(answer);
                switch (userChoice) {
                    case 1 -> alphabet = "EN_BASIC";
                    case 2 -> alphabet = "EN_ADVANCED";
                    case 3 -> alphabet = "LATIN";
                    case 4 -> alphabet = "CYRIL";
                    case 5 -> alphabet = "LATIN_MIX";
                    case 6 -> alphabet = "CYRIL_MIX";
                    default -> {
                    }
                }
            }
        } while (alphabet.isBlank());
        return alphabet;
    }
}
