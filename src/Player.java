import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Player {

    private MediaPlayer player;

    public void tocar(Musica musica) {

        Media media = new Media(new File(musica.getCaminho()).toURI().toString());

        player = new MediaPlayer(media);

        player.play();

    }
}
