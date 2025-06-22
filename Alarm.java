import java.time.LocalTime;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import javax.sound.sampled.Clip;
import java.awt.Toolkit;
import java.io.IOException;
import javax.sound.sampled.FloatControl;

public class Alarm implements Runnable {
    private final LocalTime alarm;
    private final String filePath;
    private final Scanner scanner;

    Alarm(LocalTime alarm, String filePath, Scanner scanner) {
        this.alarm = alarm;
        this.filePath = filePath;
        this.scanner = scanner;
    }

    @Override
    public void run() {

        while (LocalTime.now().isBefore(alarm)) {
            try {
                Thread.sleep(1000);
                int hours = LocalTime.now().getHour();
                int minutes = LocalTime.now().getMinute();
                int seconds = LocalTime.now().getSecond();
                System.out.printf("\r%02d:%02d:%02d", hours, minutes, seconds);
            } catch (InterruptedException e) {
                System.out.println("Thread Interruption");
            }
        }

        player(filePath);
    }

    private void player(String filePath) {
        File audio = new File(filePath);

        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(audio)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

            float volume = -15.0f;
            volumeControl.setValue(volume);
            clip.start();
            System.out.println("Press Enter to stop the alarm");
            scanner.nextLine();
            clip.stop();

        } catch (UnsupportedAudioFileException e) {
            System.out.println("Inaccessible Audio");
        } catch (LineUnavailableException e) {
            System.out.println("Unavailable Audio");
        } catch (IOException e) {
            System.out.println("Audio unable to read");
        }
    }
}