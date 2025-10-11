package ua.net.agsoft.javarush.caesar;

public class Main {

    public static void main(String[] args) {

        RunSettings runSettings = new RunSettings();
        SettingsController settingsController = new SettingsController();
        if (!settingsController.control(args, runSettings)) {
            System.out.println(Message.BAD_ARGUMENTS);
            return;
        }

        Runner runner = new Runner();
        runner.run(runSettings);
    }


}
