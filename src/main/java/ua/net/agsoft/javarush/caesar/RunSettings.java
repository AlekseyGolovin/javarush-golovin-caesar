package ua.net.agsoft.javarush.caesar;

import ua.net.agsoft.javarush.crypto.CryptoAlphabetType;
import ua.net.agsoft.javarush.util.Util;

import java.nio.file.Path;

public class RunSettings {



    private Command command;
    private Path filePath;
    private String key;
    private int offset = 0;
    private CryptoAlphabetType alphabetType;
    private Path externalWordlist;
    private boolean useFileWords;

    public RunSettings() {
        //offset = 0;
        alphabetType = CryptoAlphabetType.EN_BASIC;
        useFileWords = false;
    }

    public Path getExternalWordlistPath() {
        return externalWordlist;
    }

    public Command getCommand() {
        return command;
    }

    public int getOffset() {
        return offset;
    }

    public Path getFilePath() {
        return filePath;
    }

    public CryptoAlphabetType getAlphabetType() {
        return alphabetType;
    }

    public boolean isNeedUseFileWords() {
        return useFileWords;
    }

    public void setExternalWordlist(String commonWordsFile) {
        this.externalWordlist = Path.of(commonWordsFile);
        if (command == Command.BRUTE_FORCE && !Util.isInteger(key)) {
            // ПОдбор сдвига и ключ не число. предполагаю, что указан путь к файлу ск лючевыми словами
            if (Util.isFileExists(externalWordlist)) {
                useFileWords = true;
            } else {
                System.out.println(Message.BAD_WORDS_FILE_PATH);
            }
        }
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    public void setKey(String key) {
        this.key = key;
        offset = Util.tryToInt(key);
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setAlphabetType(CryptoAlphabetType alphabetType) {
        this.alphabetType = alphabetType;
    }

    public void setAlphabetType(String alphabetTypeString) {
        CryptoAlphabetType cryptoAlphabetType = switch (alphabetTypeString) {
            case "EN_ADVANCED" -> CryptoAlphabetType.EN_ADVANCED;
            case "LATIN" -> CryptoAlphabetType.LATIN;
            case "CYRIL" -> CryptoAlphabetType.CYRIL;
            case "LATIN_MIX" -> CryptoAlphabetType.LATIN_MIX;
            case "CYRIL_MIX" -> CryptoAlphabetType.CYRIL_MIX;
            default -> CryptoAlphabetType.EN_BASIC;
        };
        setAlphabetType(cryptoAlphabetType);
    }

    public String toString() {
        return String.format("{%s \"%s\" %d %s}", command.toString(), filePath, offset, alphabetType);
    }

    private boolean isValidCommand() {
        return (command != Command.BAD_COMMAND);
    }

    private boolean isValidFilePath() {
        if (Util.isFileExists(filePath)) {
            return true;
        } else {
            System.out.println(Message.BAD_FILE_PATH);
            return false;
        }
    }

    public boolean isValid() {
        if (!isValidCommand()) return false;
        if (!isValidFilePath()) return false;
        if ((command == Command.ENCRYPT || command == Command.DECRYPT) && !Util.isInteger(key)) {
            return false;
        }
        return true;
    }


}
