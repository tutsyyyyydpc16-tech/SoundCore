import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Janela extends JFrame {

    public Janela() {

        setTitle("SoundCore");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        Controles();
        PainelCentral();
        PainelPlaylist();
        PainelProgresso();

        reprodutor.setAoTerminar(() -> SwingUtilities.invokeLater(this::tocarProxima));
        reprodutor.setProgressoListener((atual, total) -> SwingUtilities.invokeLater(() -> {

            if (!arrastando) {
                progresso.setMaximum((int) total);
                progresso.setValue((int) atual);
            }

        }));

        carregarPlaylistSalva();

        AplicarTemaEscuro();

        addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                salvarPlaylistAtual();
                System.exit(0);
            }
        });

        setVisible(true);

    }

    private void carregarPlaylistSalva() {

        List<String> caminhos = storage.carregar();

        for (String caminho : caminhos) {

            File arquivo = new File(caminho);
            Musica m = new Musica(arquivo.getName(), caminho);
            lerMetadados(m);

            playlistModel.addElement(m);
        }
    }

    private void salvarPlaylistAtual() {

        List<Musica> todas = new ArrayList<>();

        for (int i = 0; i < playlistModel.getSize(); i++) {
            todas.add(playlistModel.getElementAt(i));
        }

        storage.salvar(todas);
    }

    public void Controles() {

        JPanel control = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton abrirMusica = new JButton("Abrir música");
        control.add(abrirMusica);
        abrirMusica.addActionListener(e -> AbrirMusica());

        JButton anteriorMusica = new JButton("⏮");
        control.add(anteriorMusica);
        anteriorMusica.addActionListener(e -> tocarAnterior());

        JButton anterior = new JButton("<<");
        control.add(anterior);
        anterior.addActionListener(e -> reprodutor.retroceder(10));

        play = new JButton("▶ Play");
        control.add(play);

        play.addActionListener(e ->alternarPlayPause());

        JButton seguinte = new JButton(">>");
        control.add(seguinte);
        seguinte.addActionListener(e -> reprodutor.avancar(10));

        JButton seguinteMusica = new JButton("⏭");
        control.add(seguinteMusica);
        seguinteMusica.addActionListener(e -> tocarProxima());

        JSlider volume = new JSlider(0, 100, 70);
        volume.setPreferredSize(new Dimension(100, 20));
        control.add(new JLabel("🔊"));
        control.add(volume);

        volume.addChangeListener(e -> reprodutor.setVolume(volume.getValue() / 100.0));

        add(control, BorderLayout.SOUTH);

        control.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    }

    public void PainelCentral() {

        JPanel central = new JPanel();
        central.setLayout(new GridLayout(3, 1));


        capa = new JLabel("[Nenhuma capa]", SwingConstants.CENTER);
        musica = new JLabel("Nenhuma música", SwingConstants.CENTER);
        artista = new JLabel("Nenhum artista", SwingConstants.CENTER);

        central.add(capa);
        central.add(musica);
        central.add(artista);

        add(central, BorderLayout.CENTER);

        central.setBorder(BorderFactory.createLineBorder(Color.BLACK));

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

            playlistModel.addElement(nova);
        }
    }

    private void alternarPlayPause() {

        if (musicaAtual == null) {

            return;
        }

        if (reprodutor.tocando()) {

            reprodutor.pausar();
            play.setText("▶ Play");
        }
        else {

            reprodutor.continuar();
            play.setText("⏸ Pause");
        }
    }

    public void PainelPlaylist() {

        playlistModel = new DefaultListModel<>();
        listaPlaylist = new JList<>(playlistModel);

        listaPlaylist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listaPlaylist.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                if (e.getClickCount() == 2) {

                    int indiceClicado = listaPlaylist.getSelectedIndex();
                    if (indiceClicado != -1) {
                        tocarDaPlaylist(indiceClicado);
                    }
                }
            }
        });

        JScrollPane scroll = new JScrollPane(listaPlaylist);
        scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JButton remover = new JButton("Remover da Playlist");
        remover.addActionListener(e -> removerSelecionado());

        JPanel painelDireito = new JPanel(new BorderLayout());
        painelDireito.setPreferredSize(new Dimension(200, 0));
        painelDireito.add(scroll, BorderLayout.CENTER);
        painelDireito.add(remover, BorderLayout.SOUTH);

        add(painelDireito, BorderLayout.EAST);
    }

    private void removerSelecionado() {

        int indice = listaPlaylist.getSelectedIndex();

        if (indice == -1) {
            return;
        }

        Musica selecionada = playlistModel.getElementAt(indice);

        if (selecionada == musicaAtual) {

            reprodutor.parar();
            musicaAtual = null;
            musica.setText("Nenhuma música");
            artista.setText("Nenhum artista");
            capa.setIcon(null);
            capa.setText("[Nenhuma capa!]");
            play.setText("▶ Play");
        }

        playlistModel.remove(indice);
    }

    private void tocarProxima() {

        int tamanho = playlistModel.getSize();

        if (tamanho == 0) {
            return;
        }

        int indiceAtual = playlistModel.indexOf(musicaAtual);
        int proximoIndice = (indiceAtual + 1) % tamanho;

        tocarDaPlaylist(proximoIndice);
    }

    private void tocarAnterior() {

        int tamanho = playlistModel.getSize();

        if (tamanho == 0) {
            return;
        }

        int indiceAtual = playlistModel.indexOf(musicaAtual);
        int anteriorIndice = (indiceAtual - 1 + tamanho) % tamanho;

        tocarDaPlaylist(anteriorIndice);
    }

    private void tocarDaPlaylist(int indice) {

        musicaAtual = playlistModel.getElementAt(indice);
        musica.setText(musicaAtual.getNome());
        reprodutor.tocar(musicaAtual);
        play.setText("⏸ Pause");

        listaPlaylist.setSelectedIndex(indice);

        atualizarCapa();
        atualizarArtista();
    }

    private void atualizarArtista() {

        if (musicaAtual.getArtista() != null) {
            artista.setText(musicaAtual.getArtista());
        }
        else {
            artista.setText("Artista não identificado");
        }
    }

    private void atualizarCapa() {

        if (musicaAtual.getCapa() != null) {

            ImageIcon icone = new ImageIcon(
                    new ImageIcon(musicaAtual.getCapa())
                            .getImage()
                            .getScaledInstance(150, 150, Image.SCALE_SMOOTH)
            );

            capa.setIcon(icone);
            capa.setText(null);

        }
        else {
            capa.setIcon(null);
            capa.setText("[Nenhuma capa!]");
        }
    }

    public void PainelProgresso() {

        progresso = new JSlider(0, 100, 0);

        progresso.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                arrastando = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                arrastando = false;
                reprodutor.seek(progresso.getValue());
            }
        });

        add(progresso, BorderLayout.NORTH);
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

    public void AplicarTemaEscuro() {

        getContentPane().setBackground(FUNDO);
        aplicarTemaComponente(getContentPane());
    }

    private void aplicarTemaComponente(Component componente) {

        if (componente instanceof JPanel) {
            componente.setBackground(FUNDO);
        }
        else if (componente instanceof JButton botao) {

            botao.setBackground(FUNDO_CLARO);
            botao.setBackground(TEXTO);
            botao.setFocusPainted(false);
            botao.setBorder(BorderFactory.createLineBorder(DESTAQUE));
        }
        else if (componente instanceof JLabel rotulo) {
            rotulo.setForeground(TEXTO);
        }
        else if (componente instanceof JList<?> lista) {
            lista.setBackground(FUNDO_CLARO);
            lista.setForeground(TEXTO);
            lista.setSelectionBackground(DESTAQUE);
            lista.setSelectionForeground(Color.WHITE);
        }
        else if (componente instanceof JScrollPane painel) {
            painel.getViewport().setBackground(FUNDO_CLARO);
            painel.setBorder(BorderFactory.createLineBorder(DESTAQUE));
        }
        else if (componente instanceof JSlider slider) {
            slider.setBackground(FUNDO);
            slider.setForeground(TEXTO);
        }
        if (componente instanceof Container container) {
            for (Component filho : container.getComponents()) {
                aplicarTemaComponente(filho);
            }
        }
    }

    private JLabel musica;
    private JLabel artista;
    private JLabel capa;
    private Musica musicaAtual;
    private String caminhoMusica;
    private Player reprodutor = new Player();
    private JButton play;
    private DefaultListModel<Musica> playlistModel;
    private JList<Musica> listaPlaylist;
    private JSlider progresso;
    private boolean arrastando = false;
    private PlaylistStorage storage = new PlaylistStorage();
    private static final Color FUNDO = new Color(30, 30, 30);
    private static final Color FUNDO_CLARO = new Color(45, 45, 45);
    private static final Color TEXTO = new Color(230, 230, 230);
    private static final Color DESTAQUE = new Color(70, 130, 180);

}
