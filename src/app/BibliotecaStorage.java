package app;

import modelo.Musica;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class BibliotecaStorage {

    private static final String PASTA_DADOS = System.getProperty("user.home") + File.separator + "SoundCoreData";
    public static final String ARQUIVO = PASTA_DADOS + File.separator + "biblioteca.txt";

    public BibliotecaStorage() {
        new File(PASTA_DADOS).mkdirs();
    }

    public void salvar(List<Musica> biblioteca) {

        try (PrintWriter writer = new PrintWriter(new FileWriter(ARQUIVO))) {

            for (Musica m : biblioteca) {
                writer.println(m.getCaminho());
            }
        } catch (Exception e) {
            System.out.println("Não consegui salvar a biblioteca: " + e.getMessage());
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
        } catch (Exception e) {
            System.out.println("Não consegui carregar a biblioteca: " + e.getMessage());
        }

        return caminhos;
    }
}
