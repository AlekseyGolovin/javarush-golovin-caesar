package ua.net.agsoft.javarush.caesar.util;

public class Util {

    public static int tryToInt(String str) {
        if (str == null || str.isBlank()) {
            return 0;
        }
        try {
            return Integer.parseInt(str.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static boolean isInteger(String str) {
        if (str == null || str.isBlank()) {
            return false;
        }
        try {
            Integer.parseInt(str.trim());
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
