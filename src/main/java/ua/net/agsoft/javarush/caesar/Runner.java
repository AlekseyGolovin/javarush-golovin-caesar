package ua.net.agsoft.javarush.caesar;

import ua.net.agsoft.javarush.crypto.Crypto;
import ua.net.agsoft.javarush.crypto.CryptoAlphabetType;
import ua.net.agsoft.javarush.crypto.CryptoFile;

import java.nio.file.Path;

public class Runner {


    private RunSettings runSettings;

    public Runner(RunSettings runSettings) {
        this.runSettings = runSettings;
    }

    public void run() {
        switch (runSettings.getCommand()) {
            case ENCRYPT -> encryptFile(runSettings);
            case DECRYPT -> decryptFile(runSettings);
            case BRUTE_FORCE -> bruteForceFile(runSettings);
            case BAD_COMMAND -> System.out.println(Message.BAD_COMMAND);
        }
    }

    private void encryptFile(RunSettings po) {
        Path filepath = po.getFilePath();
        int offset = po.getOffset();
        CryptoAlphabetType alphabetType = po.getAlphabetType();
        Path resultFilePath = getResultFilePath(filepath, Command.ENCRYPT);
        Crypto crypto = new Crypto(alphabetType);
        crypto.setOffset(offset);
        CryptoFile.encryptFile(filepath, resultFilePath, crypto);
    }

    private void decryptFile(RunSettings po) {
        Path filepath = po.getFilePath();
        int offset = po.getOffset();
        CryptoAlphabetType alphabetType = po.getAlphabetType();
        Path resultFilePath = getResultFilePath(filepath, Command.DECRYPT);
        Crypto crypto = new Crypto(alphabetType);
        crypto.setOffset(offset);
        CryptoFile.decryptFile(filepath, resultFilePath, crypto);
    }

    private void bruteForceFile(RunSettings runSettings) {
        Path filePath = runSettings.getFilePath();
        CryptoAlphabetType alphabetType = runSettings.getAlphabetType();
        Crypto crypto = new Crypto(alphabetType);
        int offset;
        if (runSettings.isNeedUseFileWords()) {
            String[] keyWords = CryptoFile.getKeyWords(runSettings.getExternalWordlistPath());
            offset = CryptoFile.getCryptoOffset(filePath, crypto, keyWords);
        } else {
            offset = CryptoFile.getCryptoOffset(filePath, crypto, null);
        }
        RunSettings runSettingsForDecrypt = new RunSettings();
        runSettingsForDecrypt.setCommand(Command.DECRYPT);
        runSettingsForDecrypt.setFilePath(filePath);
        runSettingsForDecrypt.setOffset(offset);
        runSettingsForDecrypt.setAlphabetType(alphabetType);
        decryptFile(runSettingsForDecrypt);
    }

    private Path getResultFilePath(Path filepath, Command command) {
        String added = "DECRYPTED";
        if (command == Command.ENCRYPT) added = "ENCRYPTED";
        String fileName = filepath.getFileName().toString();
        Path directory = filepath.getParent();
        int pointPos = fileName.lastIndexOf(".");
        int markPos = getMarkPos(fileName);
        String resultFileExt = fileName.substring(pointPos);
        String resultFileName;
        if (markPos >= 0) {
            resultFileName = fileName.substring(0, markPos) + "[" + added + "]" + resultFileExt;
        } else {
            resultFileName = fileName.substring(0, pointPos) + "[" + added + "]" + resultFileExt;
        }
        return directory.resolve(resultFileName);
    }

    private int getMarkPos(String fileName) {
        int markPos = -1;
        int markPosEncrypt = fileName.indexOf("[ENCRYPTED]");
        int markPosDecrypt = fileName.indexOf("[DECRYPTED]");
        if (markPosEncrypt >= 0) markPos = markPosEncrypt;
        if (markPosDecrypt >= 0 && markPosDecrypt < markPos) markPos = markPosEncrypt;
        return markPos;
    }


}
