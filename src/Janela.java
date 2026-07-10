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

public class Janela extends JFrame {

    public Janela() {

        setTitle("SoundCore");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        setVisible(true);

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
        scroll.setPreferredSize(new Dimension(200, 0));
        scroll.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(scroll, BorderLayout.EAST);
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

}
