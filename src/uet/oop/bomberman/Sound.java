package uet.oop.bomberman;

import javafx.scene.media.AudioClip;

public class Sound {
    public static AudioClip music;
    public static AudioClip lobby;
    public static AudioClip bgGame;
    public static AudioClip dead;
    public static AudioClip setBomb;
    public static AudioClip bomExplode;
    public static AudioClip itemWav;

    public static void upSound() {
        try {
            lobby = new AudioClip("file:res/sound/lobby.mp3");
            music = lobby;
            bgGame = new AudioClip("file:res/sound/bgGame.mp3");
            dead = new AudioClip("file:res/sound/dead.wav");
            setBomb = new AudioClip("file:res/sound/setBom.wav");
            bomExplode = new AudioClip("file:res/sound/bomExplode.wav");
            itemWav = new AudioClip("file:res/sound/item.wav");

            music.setCycleCount(AudioClip.INDEFINITE);
            bgGame.setCycleCount(AudioClip.INDEFINITE);
            lobby.setCycleCount(AudioClip.INDEFINITE);

            music.setVolume(0.5);
        } catch (Exception i) {
            i.printStackTrace();
        }
    }
}
