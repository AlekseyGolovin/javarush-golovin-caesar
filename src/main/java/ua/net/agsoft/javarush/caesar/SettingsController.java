package ua.net.agsoft.javarush.caesar;

import ua.net.agsoft.javarush.caesar.util.Util;

import java.nio.file.Path;
import java.util.Scanner;

public class SettingsController {


    public boolean control(String[] args, RunSettings runSettings) {
        if (controlArguments(args, runSettings)) {
            return true;
        }
        if (!requestManualInput()) {
            return false;
        }
        Command command = requestCommand();
        runSettings.setCommand(command);
        runSettings.setFilePath(requestFilePath());
        switch (command) {
            case DECRYPT, ENCRYPT:
                String key = requestKey();
                runSettings.setKey(key);
                break;
            case BRUTE_FORCE:
                String keyWordFile = requestKeyWordFile();
                runSettings.setExternalWordlist(keyWordFile);
                break;
        }
        String alphabetType = requestAlphabetType();
        runSettings.setAlphabetType(alphabetType);
        System.out.println(runSettings);
        return runSettings.isValid();
    }

    private boolean controlArguments(String[] args, RunSettings runSettings) {
        if (args.length < 2 || args.length > 4) {
            return false;
        }
        runSettings.setCommand(Command.of(args[0]));
        runSettings.setFilePath(Path.of(args[1]));
        if (args.length >= 3) {
            runSettings.setKey(args[2]);
            runSettings.setExternalWordlist(args[2]);
        }
        if (args.length == 4) {
            runSettings.setAlphabetType(args[3]);
        }
        System.out.println(runSettings);
        return runSettings.isValid();
    }

    private boolean requestManualInput() {
        Scanner scanner = new Scanner(System.in);
        boolean isManual = false;
        boolean isCorrect;
        do {
            System.out.println(Message.MANUAL_REQUEST);
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
        Command command;
        boolean isCorrect;
        do {
            System.out.println(Message.COMMAND_REQUEST);
            String answer = scanner.nextLine().toLowerCase();
            if (Util.isInteger(answer)) {
                int userChoice = Util.tryToInt(answer);
                switch (userChoice) {
                    case 1 -> command = Command.ENCRYPT;
                    case 2 -> command = Command.DECRYPT;
                    case 3 -> command = Command.BRUTE_FORCE;
                    default -> command = Command.BAD_COMMAND;
                }
            } else {
                command = Command.of(answer);
            }
            isCorrect = command != Command.BAD_COMMAND;
        } while (!isCorrect);
        return command;
    }

    private Path requestFilePath() {
        Scanner scanner = new Scanner(System.in);
        Path path;
        boolean isCorrect;
        do {
            System.out.println(Message.PATH_REQUEST);
            String answer = scanner.nextLine().toLowerCase();
            path = Path.of(answer);
            isCorrect = Util.isFileExists(path);
        } while (!isCorrect);
        return path;
    }

    private String requestKey() {
        Scanner scanner = new Scanner(System.in);
        String key;
        boolean isCorrect;
        do {
            System.out.println(Message.KEY_REQUEST);
            String answer = scanner.nextLine().toLowerCase();
            key = answer;
            isCorrect = Util.isInteger(answer);
        } while (!isCorrect);
        return key;
    }

    private String requestKeyWordFile() {
        Scanner scanner = new Scanner(System.in);
        Path path;
        boolean isCorrect;
        do {
            System.out.println(Message.KEY_WORD_REQUEST);
            String answer = scanner.nextLine().toLowerCase();
            if (answer.isBlank()) {
                return "";
            }
            path = Path.of(answer);
            isCorrect = Util.isFileExists(path);
        } while (!isCorrect);
        return path.toString();
    }

    private String requestAlphabetType() {
        Scanner scanner = new Scanner(System.in);
        String alphabet;
        boolean isCorrect;
        do {
            System.out.println(Message.ALPHABET_REQUEST);
            String answer = scanner.nextLine().toLowerCase();
            if (Util.isInteger(answer)) {
                int userChoice = Util.tryToInt(answer);
                switch (userChoice) {
                    case 1 -> alphabet = "EN_BASIC";
                    case 2 -> alphabet = "EN_ADVANCED";
                    case 3 -> alphabet = "LATIN";
                    case 4 -> alphabet = "CYRIL";
                    case 5 -> alphabet = "LATIN_MIX";
                    case 6 -> alphabet = "CYRIL_MIX";
                    default -> alphabet = "";
                }
            } else {
                alphabet = "";
            }
            isCorrect = !alphabet.isBlank();
        } while (!isCorrect);
        return "";
    }


}
