import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.time.LocalTime;

public class clock {
    public static void main(String[] args) {
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime alarm = null;
        String filePath = "song.wav";

        while (alarm == null) {
            try {
                System.out.println("Emter time to set an alarm: (HH:MM:SS): ");
                String input = scanner.nextLine();
                alarm = LocalTime.parse(input, formatter);

            } catch (DateTimeParseException e) {
                System.out.println("Invalid  Format");
            }
        }
        Alarm ac = new Alarm(alarm, filePath, scanner);
        Thread alarmThread = new Thread(ac);
        if (LocalTime.now().isAfter(alarm)) {
            System.out.println("Alarm time already passed, Please set a future time.");
            return;
        } else {
            System.out.println("Alarm set for:" + " " + alarm);
            alarmThread.start();
        }
    }

}
