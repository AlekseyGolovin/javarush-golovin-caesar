package ua.net.agsoft.javarush.caesar;

import ua.net.agsoft.javarush.caesar.crypto.Crypto;
import ua.net.agsoft.javarush.caesar.crypto.CryptoFile;

import java.nio.file.Path;

public class Runner {

    public void run(RunSettings runSettings) {

        switch (runSettings.getCommand()) {
            case ENCRYPT -> encryptFile(runSettings);
            case DECRYPT -> decryptFile(runSettings);
            case BRUTE_FORCE -> bruteForceFile(runSettings);
        }
    }

    private void encryptFile(RunSettings runSettings) {
        Path filePath = runSettings.getFilePath();
        Path resultFilePath = getResultFilePath(filePath, Command.ENCRYPT);
        Crypto crypto = new Crypto(runSettings.getAlphabetType());
        crypto.setOffset(runSettings.getOffset());
        CryptoFile cryptoFile = new CryptoFile(filePath, crypto);
        cryptoFile.encryptTo(resultFilePath);
    }

    private void decryptFile(RunSettings runSettings) {
        Path filepath = runSettings.getFilePath();
        Path resultFilePath = getResultFilePath(filepath, Command.DECRYPT);
        Crypto crypto = new Crypto(runSettings.getAlphabetType());
        crypto.setOffset(runSettings.getOffset());
        CryptoFile cryptoFile = new CryptoFile(filepath, crypto);
        cryptoFile.decryptTo(resultFilePath);
    }

    private void bruteForceFile(RunSettings runSettings) {
        Path filePath = runSettings.getFilePath();
        Crypto crypto = new Crypto(runSettings.getAlphabetType());
        CryptoFile cryptoFile = new CryptoFile(filePath, crypto);
        String[] keyWords = null;
        if (runSettings.isNeedUseFileWords()) {
            keyWords = CryptoFile.getKeyWords(runSettings.getExternalWordlistPath());
        }
        int offset = cryptoFile.calculateOptimalOffset(keyWords);
        RunSettings runSettingsForDecrypt = getSettingsForDecrypt(runSettings, offset);
        decryptFile(runSettingsForDecrypt);
    }

    private static RunSettings getSettingsForDecrypt(RunSettings runSettings, int offset) {
        RunSettings runSettingsForDecrypt = new RunSettings();
        runSettingsForDecrypt.setCommand(Command.DECRYPT);
        runSettingsForDecrypt.setFilePath(runSettings.getFilePath());
        runSettingsForDecrypt.setOffset(offset);
        runSettingsForDecrypt.setAlphabetType(runSettings.getAlphabetType());
        return runSettingsForDecrypt;
    }

    private Path getResultFilePath(Path filepath, Command command) {
        String addedMark = "DECRYPTED";
        if (command == Command.ENCRYPT) {
            addedMark = "ENCRYPTED";
        }
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
        if (markPosEncrypt >= 0) {
            markPos = markPosEncrypt;
        }
        if (markPosDecrypt >= 0 && markPosDecrypt < markPos) {
            markPos = markPosDecrypt;
        }
        return markPos;
    }


}
