package engine.audio;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Kaden Cringle
 * 10/22/2017
 */

public class AudioClip {

    private Clip clip;
    private FloatControl volume;

    public AudioClip(String path) {
        try {
            InputStream is = AudioClip.class.getResourceAsStream(path);
            InputStream buffer = new BufferedInputStream(is);
            AudioInputStream ais = AudioSystem.getAudioInputStream(buffer);
            AudioFormat format = ais.getFormat();
            AudioFormat newFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(),
                    16, format.getChannels(), format.getChannels() * 2, format.getSampleRate(),
                    false);
            AudioInputStream decodedAis = AudioSystem.getAudioInputStream(newFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(decodedAis);
            volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch(UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        if(clip != null) {
            stop();
            clip.setFramePosition(0);
            while(!clip.isRunning())
                clip.start();
        }
    }

    public void stop() {
        if(clip.isRunning())
            clip.stop();
    }

    public void close() {
        stop();
        clip.drain();
        clip.close();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        start();
    }

    public void setVolume(float value) {
        this.volume.setValue(value);
    }

    public boolean isRunning() {
        return clip.isRunning();
    }
}
