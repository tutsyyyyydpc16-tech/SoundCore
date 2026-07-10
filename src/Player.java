import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.awt.event.MouseEvent;
import java.io.File;
import javafx.util.Duration;

import javax.swing.*;

public class Player {

    private MediaPlayer player;

    public void tocar(Musica musica) {

        if (player != null) {

            player.stop();
            player.dispose();
        }

        Media media = new Media(new File(musica.getCaminho()).toURI().toString());

        player = new MediaPlayer(media);
        player.setVolume(volumeAtual);

        player.setOnEndOfMedia(() -> {

            if (aoTerminar != null) {
                aoTerminar.run();
            }
        });

        player.currentTimeProperty().addListener((obs, tempoAntigo, tempoNovo) -> {
            if (progressoListener != null && player.getTotalDuration() != null) {
                progressoListener.aoAtualizar(
                        tempoNovo.toSeconds(),
                        player.getTotalDuration().toSeconds()
                );
            }
        });

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

    public void avancar(double segundos) {

        if (player == null) {

            return;
        }

        Duration atual = player.getCurrentTime();
        Duration total = player.getTotalDuration();
        Duration novo = atual.add(Duration.seconds(segundos));

        if (total != null && novo.greaterThan(total)) {
            novo = total;
        }

        player.seek(novo);
    }

    public void retroceder(double segundos) {

        if (player == null) {
            return;
        }

        Duration atual = player.getCurrentTime();
        Duration novo = atual.subtract(Duration.seconds(segundos));

        if (novo.lessThan(Duration.ZERO)) {
            novo = Duration.ZERO;
        }

        player.seek(novo);
    }

    public void setAoTerminar(Runnable acao) {
        this.aoTerminar = acao;
    }

    public interface ProgressoListener {
        void aoAtualizar(double atualSegundos, double totalSegundos);
    }

    public void setProgressoListener(ProgressoListener listener) {
        this.progressoListener = listener;
    }

    public void seek(double segundos) {
        if (player != null) {
            player.seek(Duration.seconds(segundos));
        }
    }

    public void setVolume(double volume) {
        if (player != null) {
            player.setVolume(volume);
        }
    }

    private Runnable aoTerminar;
    private ProgressoListener progressoListener;
    private double volumeAtual = 0.7;
}
