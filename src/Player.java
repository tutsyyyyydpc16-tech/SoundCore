import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Player {

    private MediaPlayer player;

    public Player() {


    }



    public void tocar(Musica musica) {

        if (player != null) {

            player.stop();
            player.dispose();
        }

        Media media = new Media(new File(musica.getCaminho()).toURI().toString());

        player = new MediaPlayer(media);
        player.play();

    }

    public void pausar() {

        if (player != null) {

            player.pause();
        }
    }

    public void continuar() {

        if (player != null) {

            player.play();
        }
    }

    public boolean tocando() {

        return player != null && player.getStatus() == MediaPlayer.Status.PLAYING;
    }
}
