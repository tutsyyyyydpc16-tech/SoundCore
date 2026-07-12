package app;

import java.util.List;
import modelo.Musica;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;
import player.Player;
import ui.BarraTitulo;
import ui.EqualizerBarras;
import ui.InterfaceSoundCore;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;


public class Janela extends JFrame {

    public Janela() {

        setTitle("SoundCore");
        setUndecorated(true);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        DefinirIcone();

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(Color.BLACK);

        BarraTitulo barraTitulo = new BarraTitulo(this);
        raiz.add(barraTitulo, BorderLayout.NORTH);

        InterfaceSoundCore ui = new InterfaceSoundCore(this);
        raiz.add(ui, BorderLayout.CENTER);

        add(raiz);

        this.ui = ui;

        reprodutor.setAoTerminar(() -> SwingUtilities.invokeLater(this::tocarProxima));
        reprodutor.setProgressoListener((atual, total) -> SwingUtilities.invokeLater(() -> {

            if (!arrastando) {
                JSlider progresso = ui.getPainelDireito().getProgresso();

                progresso.setMaximum((int) total);
                progresso.setValue((int) atual);
            }

            ui.getPainelDireito().atualizarTempo(atual, total);

        }));

        carregarPlaylistSalva();

        addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                salvarPlaylistAtual();
                System.exit(0);
            }
        });

        addComponentListener(new java.awt.event.ComponentAdapter() {

            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                repaint();
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

        List<String> caminhos = storage.carregar();

        for (String caminho : caminhos) {

            File arquivo = new File(caminho);
            Musica m = new Musica(arquivo.getName(), caminho);
            lerMetadados(m);

            ui.getPainelDireito().adicionarMusica(m);
        }
    }

    private void salvarPlaylistAtual() {

        List<Musica> todas = new ArrayList<>();

        DefaultListModel<Musica> lista =
                ui.getPainelDireito().getPlaylistModel();

        for (int i = 0; i < lista.size(); i++) {
            todas.add(lista.getElementAt(i));
        }

        storage.salvar(todas);
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
            ui.getPainelEsquerdo().getEqualizer().setAtivo(false);
        }
        else {
            reprodutor.continuar();
            ui.getPainelDireito().atualizarBotaoPlay(true);
            ui.getPainelEsquerdo().getEqualizer().setAtivo(true);
        }
    }

    public void tocarProxima() {

        JList<Musica> lista = ui.getPainelDireito().getListaPlaylist();
        int tamanho = lista.getModel().getSize();

        if (tamanho == 0) {
            return;
        }

        if (repetirAtual) {
            tocarMusica(musicaAtual);
            return;
        }

        int indice;

        if (modoAleatorio) {

            int indiceAtual = lista.getSelectedIndex();
            java.util.Random random = new java.util.Random();

            do {
                indice = random.nextInt(tamanho);
            } while (tamanho > 1 && indice == indiceAtual);
        }
        else {

            int indiceAtual = lista.getSelectedIndex();

            if (indiceAtual == -1) {
                return;
            }

            if (indiceAtual < tamanho -1) {
                indice = indiceAtual + 1;
            }
            else {
                indice = 0;
            }
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

            m.setDuracaoSegundos(audioFile.getAudioHeader().getTrackLength());

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
        ui.getPainelEsquerdo().atualizarCapa(musica.getCapa());
        ui.getPainelEsquerdo().atualizarInfo(musica.getNome(), musica.getArtista());
        ui.getPainelEsquerdo().getEqualizer().setAtivo(true);
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
        reprodutor.setVolume(valor / 100.0);
    }

    public void alternarRepetir() {
        repetirAtual = !repetirAtual;
        ui.getPainelDireito().atualizarEstadoRepeat(repetirAtual);

    }

    public void alternarAleatorio() {
        modoAleatorio = !modoAleatorio;
        ui.getPainelDireito().atualizarEstadoShuffle(modoAleatorio);
    }

    private Player reprodutor = new Player();
    private app.PlaylistStorage storage = new app.PlaylistStorage();
    private InterfaceSoundCore ui;
    private Musica musicaAtual;

    private boolean arrastando = false;
    private boolean repetirAtual = false;
    private boolean modoAleatorio = false;
}
