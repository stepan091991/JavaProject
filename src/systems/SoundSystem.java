package systems;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundSystem {
    private Map<String, Clip> sounds;
    private boolean enabled = true;
    private float volume = 1.0f;

    public SoundSystem() {
        this.sounds = new HashMap<>();
    }

    public void loadSound(String name, String path) {
        try {
            URL url = getClass().getResource(path);
            if (url == null) {
                System.err.println("Sound file not found: " + path);
                return;
            }

            AudioInputStream audioIn = javax.sound.sampled.AudioSystem.getAudioInputStream(url);
            Clip clip = javax.sound.sampled.AudioSystem.getClip();
            clip.open(audioIn);
            sounds.put(name, clip);

        } catch (UnsupportedAudioFileException e) {
            System.err.println("Unsupported audio format: " + path);
        } catch (IOException e) {
            System.err.println("Error reading sound file: " + path);
        } catch (LineUnavailableException e) {
            System.err.println("Audio line unavailable: " + path);
        }
    }

    public void play(String name) {
        if (!enabled) return;

        Clip clip = sounds.get(name);
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        } else {
            System.err.println("Sound not loaded: " + name);
        }
    }

    public void loop(String name) {
        if (!enabled) return;

        Clip clip = sounds.get(name);
        if (clip != null) {
            clip.setFramePosition(0);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            System.err.println("Sound not loaded: " + name);
        }
    }

    public void stop(String name) {
        Clip clip = sounds.get(name);
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void setVolume(float volume) {
        this.volume = Math.max(0, Math.min(1, volume));

        for (Clip clip : sounds.values()) {
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                gainControl.setValue(dB);
            }
        }
    }

    public boolean isPlaying(String name) {
        Clip clip = sounds.get(name);
        return clip != null && clip.isRunning();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void dispose() {
        for (Clip clip : sounds.values()) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.close();
        }
        sounds.clear();
    }

    public String[] getLoadedSounds() {
        return sounds.keySet().toArray(new String[0]);
    }
}