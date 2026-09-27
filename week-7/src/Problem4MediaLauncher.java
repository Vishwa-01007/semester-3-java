interface Playable {

    String play();

    String play(int fromSecond);

    String pause();
}

abstract class MediaFile {

    private static int counter = 1000;

    private final String fileId;

    public MediaFile() {

        counter++;

        fileId = "MF-" + counter;
    }

    public abstract String getFormatInfo();

    public String getFileId() {
        return fileId;
    }
}

class AudioFile extends MediaFile
        implements Playable {

    private String title;

    public AudioFile(String title) {
        this.title = title;
    }

    @Override
    public String play() {

        return "Playing audio: "
                + title;
    }

    @Override
    public String play(int fromSecond) {

        int minutes = fromSecond / 60;
        int seconds = fromSecond % 60;

        return String.format(
                "Playing audio: %s from %d:%02d",
                title,
                minutes,
                seconds
        );
    }

    @Override
    public String pause() {

        return "Paused audio: " + title;
    }

    @Override
    public String getFormatInfo() {

        return "Audio file, ID: "
                + getFileId();
    }
}

class Podcast implements Playable {

    private String showName;
    private int episodeNumber;

    public Podcast(
            String showName,
            int episodeNumber) {

        this.showName = showName;
        this.episodeNumber = episodeNumber;
    }

    @Override
    public String play() {

        return "Streaming episode "
                + episodeNumber
                + " of "
                + showName;
    }

    @Override
    public String play(int fromSecond) {

        return "Streaming episode "
                + episodeNumber
                + " of "
                + showName
                + " from "
                + fromSecond
                + " seconds";
    }

    @Override
    public String pause() {

        return "Paused podcast: "
                + showName;
    }
}

public class Problem4MediaLauncher {

    static void launchAll(
            Playable[] items) {

        for (Playable item : items) {
            System.out.println(
                    item.play()
            );
        }
    }

    public static void main(String[] args) {

        AudioFile a =
                new AudioFile("Morning Jazz");

        System.out.println(a.play());

        System.out.println(a.play(30));

        System.out.println(
                a.getFormatInfo()
        );

        Podcast p =
                new Podcast(
                        "Tech Talk",
                        12
                );

        System.out.println(p.play());

        Playable ref = a;
        // Upcasting: AudioFile reference
        // stored as Playable interface type.

        System.out.println(ref.play());

        launchAll(
                new Playable[]{ref, p}
        );
    }
}