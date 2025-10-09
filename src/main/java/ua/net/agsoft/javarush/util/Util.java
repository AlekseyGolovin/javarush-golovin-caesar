package ua.net.agsoft.javarush.util;

import java.nio.file.Files;
import java.nio.file.Path;

public class Util {

    public static boolean isFileExists(Path filepath) {
        return Files.isRegularFile(filepath);
    }

    public static int tryToInt(String str) {
        if (str == null || str.isEmpty()) return 0;
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static boolean isInteger(String str) {
        if (str == null || str.isEmpty()) return false;
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String prepareTextForAnalysis(String line) {
        String regex = "[^\\p{L}\\s]";
        String clearLine = line.replaceAll(regex, " ").trim().toLowerCase();
        return clearLine.replaceAll("\\s+", " ");
    }
}
