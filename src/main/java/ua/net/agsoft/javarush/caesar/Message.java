package ua.net.agsoft.javarush.caesar;

public class Message {

    // @formatter:off

    public static final String BAD_COMMAND = "BAD command. Possible options: ENCRYPT, DECRYPT or BRUTE_FORCE";
    public static final String BAD_FILE_PATH = "BAD file path. Ensure the file exists";
    public static final String BAD_WORDS_FILE_PATH = "BAD key-words file path. Ensure the file exists";

    public static final String BAD_ARGUMENTS =
            "Please use the following command-line format:\n" +
            "command filePath [key] [alphabet]\n" +
            "command - a required argument that specifies the action. [ENCRYPT | DECRYPT | BRUTE_FORCE]\n" +
            "filePath - a required argument representing the absolute path to the file.\n" +
            "key - a required argument for the ENCRYPT and DECRYPT commands, integer — alphabetic offset, or path for keyword file\n" +
            "alphabet - a list of characters for encryption/decryption. " +
            "[EN_BASIC | EN_ADVANCED | LATIN | CYRIL | LATIN_MIX | CYRIL_MIX]\n" +
            "EN_BASIC - Basic Latin Alphabet. Only lowercase and uppercase Latin letters.\n" +
            "EN_ADVANCED - Extended Latin Alphabet. Basic Latin alphabet plus common punctuation and space.\n" +
            "LATIN - Full Latin Set. Includes digits, Latin letters, and a complete set of ASCII special characters.\n" +
            "CYRIL - Full Cyrillic Set. Includes everything from LATIN plus all Cyrillic letters\n" +
            "LATIN_MIX - Mixed Latin Key. Same characters as LATIN, but randomly shuffled.\n" +
            "CYRIL_MIX - Mixed Cyrillic Key: Same characters as CYRIL, but randomly shuffled.";

    public static final String MANUAL_REQUEST = "Unrecognized launch arguments. Enter parameters manually? [Y/N]";

    public static final String COMMAND_REQUEST =
            "Please specify the operation you wish to perform:\n" +
            "1 - ENCRYPT\n" +
            "2 - DECRYPT\n" +
            "3 - BRUTE FORCE";

    public static final String PATH_REQUEST = "Please provide the absolute path to the file";

    public static final String KEY_REQUEST = "Enter the cipher key (the integer number of places to shift in the alphabet)";

    public static final String KEY_WORD_REQUEST =
            "Please enter the full path to the dictionary file,\n" +
            "or leave the line blank and press Enter to skip";

    public static final String ALPHABET_REQUEST =
            "Please enter the alphabet string to be used for encryption/decryption:\n" +
            "1 - EN_BASIC - Basic Latin Alphabet. Only lowercase and uppercase Latin letters.\n" +
            "2 - EN_ADVANCED - Extended Latin Alphabet. Basic Latin alphabet plus common punctuation and space.\n" +
            "3 - LATIN - Full Latin Set. Includes digits, Latin letters, and a complete set of ASCII special characters.\n" +
            "4 - CYRIL - Full Cyrillic Set. Includes everything from LATIN plus all Cyrillic letters\n" +
            "5 - LATIN_MIX - Mixed Latin Key. Same characters as LATIN, but randomly shuffled.\n" +
            "6 - CYRIL_MIX - Mixed Cyrillic Key: Same characters as CYRIL, but randomly shuffled.";

    // @formatter:on


}
