package ua.net.agsoft.javarush.caesar;

import ua.net.agsoft.javarush.crypto.Crypto;
import ua.net.agsoft.javarush.crypto.CryptoAlphabetType;
import ua.net.agsoft.javarush.crypto.CryptoFile;

import java.nio.file.Path;

public class Runner {

    //private final RunSettings runSettings;

    public Runner() {
    }

    public void run(RunSettings runSettings) {
        switch (runSettings.getCommand()) {
            case ENCRYPT -> encryptFile(runSettings);
            case DECRYPT -> decryptFile(runSettings);
            case BRUTE_FORCE -> bruteForceFile(runSettings);
            case BAD_COMMAND -> System.out.println(Message.BAD_COMMAND);
        }
    }

    private void encryptFile(RunSettings runSettings) {
        Path filePath = runSettings.getFilePath();
        int offset = runSettings.getOffset();
        CryptoAlphabetType alphabetType = runSettings.getAlphabetType();
        Path resultFilePath = getResultFilePath(filePath, Command.ENCRYPT);
        Crypto crypto = new Crypto(alphabetType);
        crypto.setOffset(offset);
        CryptoFile cryptoFile = new CryptoFile(filePath, crypto);
        cryptoFile.encryptTo(resultFilePath);
    }

    private void decryptFile(RunSettings runSettings) {
        Path filepath = runSettings.getFilePath();
        int offset = runSettings.getOffset();
        CryptoAlphabetType alphabetType = runSettings.getAlphabetType();
        Path resultFilePath = getResultFilePath(filepath, Command.DECRYPT);
        Crypto crypto = new Crypto(alphabetType);
        crypto.setOffset(offset);
        CryptoFile cryptoFile = new CryptoFile(filepath, crypto);
        cryptoFile.decryptTo(resultFilePath);
    }

    private void bruteForceFile(RunSettings runSettings) {
        Path filePath = runSettings.getFilePath();
        CryptoAlphabetType alphabetType = runSettings.getAlphabetType();
        Crypto crypto = new Crypto(alphabetType);
        int offset;
        CryptoFile cryptoFile = new CryptoFile(filePath, crypto);
        if (runSettings.isNeedUseFileWords()) {
            String[] keyWords = CryptoFile.getKeyWords(runSettings.getExternalWordlistPath());
            offset = cryptoFile.getCryptoOffset(keyWords);
        } else {
            offset = cryptoFile.getCryptoOffset(null);
        }
        RunSettings runSettingsForDecrypt = new RunSettings();
        runSettingsForDecrypt.setCommand(Command.DECRYPT);
        runSettingsForDecrypt.setFilePath(filePath);
        runSettingsForDecrypt.setOffset(offset);
        runSettingsForDecrypt.setAlphabetType(alphabetType);
        decryptFile(runSettingsForDecrypt);
    }

    private Path getResultFilePath(Path filepath, Command command) {
        String addedMark = "DECRYPTED";
        if (command == Command.ENCRYPT) addedMark = "ENCRYPTED";
        String fileName = filepath.getFileName().toString();
        Path directory = filepath.getParent();
        int pointPos = fileName.lastIndexOf(".");
        int markPos = getMarkPos(fileName);
        String resultFileExt = fileName.substring(pointPos);
        String resultFileName;
        if (markPos >= 0) {
            resultFileName = fileName.substring(0, markPos) + "[" + addedMark + "]" + resultFileExt;
        } else {
            resultFileName = fileName.substring(0, pointPos) + "[" + addedMark + "]" + resultFileExt;
        }
        return directory.resolve(resultFileName);
    }

    private int getMarkPos(String fileName) {
        int markPos = -1;
        int markPosEncrypt = fileName.indexOf("[ENCRYPTED]");
        int markPosDecrypt = fileName.indexOf("[DECRYPTED]");
        if (markPosEncrypt >= 0) markPos = markPosEncrypt;
        if (markPosDecrypt >= 0 && markPosDecrypt < markPos) markPos = markPosDecrypt;
        return markPos;
    }


}
