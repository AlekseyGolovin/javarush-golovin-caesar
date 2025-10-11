package ua.net.agsoft.javarush.crypto;

import ua.net.agsoft.javarush.util.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CryptoFile {

    private Path srcFilePath;
    private Crypto crypto;

    public CryptoFile(Path srcFilePath, Crypto crypto){
        this.srcFilePath = srcFilePath;
        this.crypto = crypto;
    }

    public void encryptTo(Path desFilePath) {
        try (BufferedReader srcReader = Files.newBufferedReader(srcFilePath);
             BufferedWriter desWriter = Files.newBufferedWriter(desFilePath)) {
            int srcInt;
            while ((srcInt = srcReader.read()) != -1) {
                char srcChar = (char) srcInt;
                char desChar = crypto.encrypt(srcChar);
                desWriter.append(desChar);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getCryptoOffsetFromMap(HashMap<Integer, Integer> offsetRate) {
        int ratePos = 0;
        int maxRate = 0;
        for (var pair : offsetRate.entrySet()) {
            Integer key = pair.getKey();
            Integer value = pair.getValue();
            if (value > maxRate) {
                ratePos = key;
                maxRate = value;
            }
        }
        System.out.println("getCryptoOffset BEST: " + ratePos);
        return ratePos;
    }

    private static int getRate(String text) {
        String textForAnalysis = Util.prepareTextForAnalysis(text);
        int enRate = getEnRate(textForAnalysis);
        System.out.println("getRate EN: " + enRate);
        int uaRate = getUaRate(textForAnalysis);
        System.out.println("getRate UA: " + uaRate);
        int ruRate = getRuRate(textForAnalysis);
        System.out.println("getRate RU: " + ruRate);
        int maxRate = enRate;
        if (uaRate > maxRate) maxRate = uaRate;
        if (ruRate > maxRate) maxRate = ruRate;
        return maxRate;
    }

    private static int getEnRate(String text) {
        return getRate(text, CommonWords.EN);
    }

    private static int getUaRate(String text) {
        return getRate(text, CommonWords.UA);
    }

    private static int getRuRate(String text) {
        return getRate(text, CommonWords.RU);
    }

    private static int getRate(String text, String[] commonWords) {
        if (commonWords == null) return getRate(text);
        text = " " + text + " ";
        int rate = 0;
        for (String commonWord : commonWords) {
            int wordLength = commonWord.length();
            String word = " " + commonWord + " ";
            int lastPos = text.indexOf(word);
            if(lastPos == -1) continue;
            rate++;
            do {
                lastPos = text.indexOf(word, lastPos + wordLength);
                if (lastPos != -1) rate++;
            } while (lastPos != -1);
        }
        return rate;
    }

    public static String[] getKeyWords(Path filePath) {
        HashSet<String> keyWordSet = new HashSet<>();
        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                String lineForAnalysis = Util.prepareTextForAnalysis(line);
                String[] keyWords = lineForAnalysis.split(" ");
                Collections.addAll(keyWordSet, keyWords);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return (String[])keyWordSet.toArray();
    }

    public void decryptTo(Path desFilePath) {
        try (BufferedReader srcReader = Files.newBufferedReader(srcFilePath);
             BufferedWriter desWriter = Files.newBufferedWriter(desFilePath)) {
            int srcInt;
            while ((srcInt = srcReader.read()) != -1) {
                char srcChar = (char) srcInt;
                char desChar = crypto.decrypt(srcChar);
                desWriter.append(desChar);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getCryptoOffset(String[] keyWords) {
        HashMap<Integer, Integer> offsetRate = new HashMap<>();
        crypto.setOffset(0);
        do {
            int offset = crypto.getOffset();
            int rate = getRate(keyWords);
            System.out.println("getCryptoOffset [" + offset + "]: " + rate);
            offsetRate.put(offset, rate);
        } while (crypto.canSetNextOffset());
        return getCryptoOffsetFromMap(offsetRate);
    }

    private int getRate(String[] keyWords) {
        char[] srcFragment = new char[512];
        char[] desFragment = new char[512];
        try (BufferedReader srcReader = Files.newBufferedReader(srcFilePath)) {
            int bufLength = srcReader.read(srcFragment);
            for (int i = 0; i < bufLength; i++) {
                desFragment[i] = crypto.decrypt(srcFragment[i]);
            }
            String text = new String(desFragment, 0, bufLength);
            return getRate(text, keyWords);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }





}
