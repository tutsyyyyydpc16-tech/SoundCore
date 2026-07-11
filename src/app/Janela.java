package app;

import modelo.Musica;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;
import player.Player;
import ui.InterfaceSoundCore;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;


public class Janela extends JFrame {

    public Janela() {

        setTitle("SoundCore");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        DefinirIcone();

        InterfaceSoundCore ui = new InterfaceSoundCore(this);

        add(ui);

        this.ui = ui;

        reprodutor.setAoTerminar(() -> SwingUtilities.invokeLater(this::tocarProxima));
        reprodutor.setProgressoListener((atual, total) -> SwingUtilities.invokeLater(() -> {

            if (!arrastando) {
                JSlider progresso = ui.getPainelDireito().getProgresso();

                progresso.setMaximum((int) total);
                progresso.setValue((int) atual);
            }

        }));

        carregarPlaylistSalva();

        addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                salvarPlaylistAtual();
                System.exit(0);
            }
        });

        setVisible(true);

    }

    private void DefinirIcone() {

        try {
            Image icone = new ImageIcon("Resource/SoundCore.png").getImage();
            setIconImage(icone);
        } catch (Exception e) {
            System.out.println("Não consegui carregar o ícone: " + e.getMessage());
        }
    }

    private void carregarPlaylistSalva() {

        /*List<String> caminhos = storage.carregar();

        for (String caminho : caminhos) {

            File arquivo = new File(caminho);
            Musica m = new Musica(arquivo.getName(), caminho);
            lerMetadados(m);

            ui.getPainelDireito()
                    .getPlaylistModel()
                    .addElement(m);
        }*/
    }

    private void salvarPlaylistAtual() {

        /*List<Musica> todas = new ArrayList<>();

        DefaultListModel<Musica> lista =
                ui.getPainelDireito().getPlaylistModel();

        for (int i = 0; i < lista.size(); i++) {
            todas.add(lista.getElementAt(i));
        }

        storage.salvar(todas);*/
    }

    public void AbrirMusica() {

        JFileChooser musicas = new JFileChooser();
        musicas.setCurrentDirectory(new File("."));

        FileNameExtensionFilter filtros = new FileNameExtensionFilter("Músicas (*.mp3)",
                "mp3");

        musicas.setFileFilter(filtros);
        musicas.setAcceptAllFileFilterUsed(false);

        int result = musicas.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {

            Musica nova = new Musica(
                    musicas.getSelectedFile().getName(),
                    musicas.getSelectedFile().getPath()
            );

            lerMetadados(nova);

            ui.getPainelDireito().adicionarMusica(nova);
        }
    }

    public void alternarPlayPause() {

        if (musicaAtual == null) {
            return;
        }

        if (reprodutor.tocando()) {
            reprodutor.pausar();
            ui.getPainelDireito().atualizarBotaoPlay(false);
        }
        else {
            reprodutor.continuar();
            ui.getPainelDireito().atualizarBotaoPlay(true);

        }
    }

    public void tocarProxima() {

        JList<Musica> lista = ui.getPainelDireito().getListaPlaylist();

        int indice = lista.getSelectedIndex();

        if (indice == -1) {
            return;
        }

        if (indice < lista.getModel().getSize() -1) {
            indice++;
        }
        else {
            indice = 0;
        }

        lista.setSelectedIndex(indice);

        tocarMusica(lista.getSelectedValue());
    }

    public void tocarAnterior() {

        JList<Musica> lista = ui.getPainelDireito().getListaPlaylist();

        int indice = lista.getSelectedIndex();

        if (indice == -1) {
            return;
        }

        if (indice > 0) {
            indice--;
        }
        else {
            indice = lista.getModel().getSize() -1;
        }

        lista.setSelectedIndex(indice);

        tocarMusica(lista.getSelectedValue());
    }

    private void lerMetadados(Musica m) {

        try {

            AudioFile audioFile = AudioFileIO.read(new File(m.getCaminho()));
            Tag tag = audioFile.getTag();

            if (tag != null) {

                Artwork artwork = tag.getFirstArtwork();

                if (artwork != null) {
                    m.setCapa(artwork.getBinaryData());
                }

                String nomeArtista = tag.getFirst(FieldKey.ARTIST);
                if (nomeArtista != null && !nomeArtista.isEmpty()) {
                    m.setArtista(nomeArtista);
                }
            }
        } catch (Exception e) {
            System.out.println("Não consegui ler metadados de " + m.getNome());
        }
    }

    public void tocarMusica(Musica musica) {
        musicaAtual = musica;
        reprodutor.tocar(musica);

        ui.getPainelDireito().atualizarBotaoPlay(true);
    }
    public void adiantarMusica() {
        reprodutor.avancar(10);
    }

    public void atrasarMusica() {
        reprodutor.retroceder(10);
    }

    public void irParaSegundos(int segundo) {
        reprodutor.seek(segundo);
    }

    public void setArrastando(boolean arrastando) {
        this.arrastando = arrastando;
    }

    public void alterarVolume(int valor) {
        reprodutor.setVolume(valor);
    }

    private Player reprodutor = new Player();
    //private PlaylistStorage storage = new PlaylistStorage();
    private InterfaceSoundCore ui;
    private Musica musicaAtual;
    private boolean arrastando = false;

}
