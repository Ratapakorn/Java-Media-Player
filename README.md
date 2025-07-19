# 🎵 JavaFX Media Player

A basic media player built with Java and JavaFX, designed to play various types of audio files with a graphical interface. The project is modular and demonstrates object-oriented principles, factory design pattern, iterator pattern, and JavaFX for GUI development.

---

## 🚀 Features

- Play, pause, and stop audio files
- WAV and tagged audio file support
- Playlist management
- Iterator pattern for looping through songs
- JavaFX-based user interface

---

## 🧱 Project Structure

The development process is split into **five main parts**:

### 1️⃣ Creating the `AudioFile` Class

This is the base abstract class that defines shared properties and methods for all types of audio files. It includes metadata such as file path and abstract methods for playing the file and retrieving information.

```java
public abstract class AudioFile {
    protected String filepath;

    public AudioFile(String filepath) {
        this.filepath = filepath;
    }

    public abstract void play();
    public abstract String getInfo();
}
```

2️⃣ Creating the SampledFile, WAVFile, and TaggedFile Classes
These classes inherit from AudioFile and implement specific behaviors:

SampledFile: A generic audio file representation.

WAVFile: Handles uncompressed WAV audio files.

TaggedFile: Supports audio files with metadata like title, artist, and album.

Example:

```java
public class WAVFile extends SampledFile {
    public WAVFile(String filepath) {
        super(filepath);
    }

    @Override
    public void play() {
        // WAV-specific playback code
    }

    @Override
    public String getInfo() {
        return "WAV File: " + filepath;
    }
}
```

3️⃣ AudioFile Factory & Playlist Class
AudioFileFactory: Determines the appropriate subclass of AudioFile based on the file extension or content.

Example:

```java
public class AudioFileFactory {
    public static AudioFile createAudioFile(File file) {
        String path = file.getAbsolutePath().toLowerCase();
        if (path.endsWith(".wav")) {
            return new WAVFile(path);
        } else if (path.endsWith(".mp3")) {
            return new TaggedFile(path);
        } else {
            return new SampledFile(path);
        }
    }
}
```

Playlist: Stores a list of AudioFile instances. Allows adding, removing, and accessing files.

Example:

public class Playlist {
    private List<AudioFile> tracks = new ArrayList<>();

    public void addFile(AudioFile file) {
        tracks.add(file);
    }

    public List<AudioFile> getTracks() {
        return tracks;
    }
}

4️⃣ Iterator for Looping Through Songs
Implements the Iterator pattern for traversing through the playlist.

Example:

```java
public class PlaylistIterator implements Iterator<AudioFile> {
    private int currentIndex = 0;
    private List<AudioFile> files;

    public PlaylistIterator(List<AudioFile> files) {
        this.files = files;
    }

    @Override
    public boolean hasNext() {
        return currentIndex < files.size();
    }

    @Override
    public AudioFile next() {
        return files.get(currentIndex++);
    }
}
```

5️⃣ Building the User Interface (Player.java)
The Player class sets up the GUI using JavaFX. It includes:

File chooser for importing audio files

Playback controls (play, pause, stop)

Volume slider

Track metadata display

Example:

```java
public class Player extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Load FXML, initialize buttons, bind actions to AudioFile methods
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

🧪 Requirements
Java 11 or higher

JavaFX SDK

An IDE such as IntelliJ IDEA, Eclipse, or VS Code with Java support

📦 How to Run
Clone the repository:
git clone https://github.com/yourusername/javafx-media-player.git
cd javafx-media-player

Open the project in your IDE and configure JavaFX settings (module path, VM arguments).

Run Player.java as a JavaFX application.
