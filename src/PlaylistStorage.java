import modelo.Musica;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class PlaylistStorage {

    private static final String ARQUIVO = "playlist.txt";

    public void salvar(List<Musica> playlist) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO))) {

            for (Musica m : playlist) {
                writer.println(m.getCaminho());
            }

        } catch (Exception e) {
            System.out.println("Não conseguir salvar a playlist: " + e.getMessage());
        }
    }

    public List<String> carregar() {

        List<String> caminhos = new ArrayList<>();
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            return caminhos;
        }

        try {

            List<String> linhas = Files.readAllLines(arquivo.toPath());

            for (String linha : linhas) {
                if (!linha.isBlank() && new File(linha).exists()) {
                    caminhos.add(linha);
                }
            }

        } catch (IOException e) {
            System.out.println("Não consegui carregar a playlist: " + e.getMessage());
        }

        return caminhos;
    }

    public void setVolume(int volume) {

    }
}
