package ua.net.agsoft.javarush.caesar;

public class Main {

    private static final String BAD_ARGUMENTS = """
            Please use the following command-line format:
            command filePath [key] [alphabet]
            command - a required argument that specifies the action. [ENCRYPT | DECRYPT | BRUTE_FORCE]
            filePath - a required argument representing the absolute path to the file.
            key - a required argument for the ENCRYPT and DECRYPT commands, \
            integer — alphabetic offset, or path for keyword file
            alphabet - a list of characters for encryption/decryption. \
            [EN_BASIC | EN_ADVANCED | LATIN | CYRIL | LATIN_MIX | CYRIL_MIX]
            EN_BASIC - Basic Latin Alphabet. Only lowercase and uppercase Latin letters.
            EN_ADVANCED - Extended Latin Alphabet. Basic Latin alphabet plus common punctuation and space.
            LATIN - Full Latin Set. Includes digits, Latin letters, and a complete set of ASCII special characters.
            CYRIL - Full Cyrillic Set. Includes everything from LATIN plus all Cyrillic letters
            LATIN_MIX - Mixed Latin Key. Same characters as LATIN, but randomly shuffled.
            CYRIL_MIX - Mixed Cyrillic Key: Same characters as CYRIL, but randomly shuffled.""";

    public static void main(String[] args) {
        RunSettings runSettings = getRunSettings(args);
        if (runSettings == null) {
            return;
        }
        System.out.println(runSettings);
        Runner runner = new Runner();
        runner.run(runSettings);
    }

    private static RunSettings getRunSettings(String[] args) {
        SettingsController settingsController = new SettingsController();
        RunSettings runSettings = settingsController.getSettingsFromArguments(args);
        if (!runSettings.isValid()) {
            runSettings = settingsController.getManualSettings();
        }
        if (!runSettings.isValid()) {
            System.out.println(BAD_ARGUMENTS);
            return null;
        }
        return runSettings;
    }


}
