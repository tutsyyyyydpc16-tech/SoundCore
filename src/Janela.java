import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class Janela extends JFrame {

    public Janela() {

        setTitle("SoundCore");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Controles();
        PainelCentral();

        setVisible(true);

    }

    public void Controles() {

        JPanel control = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton abrirMusica = new JButton("Abrir música");
        control.add(abrirMusica);
        abrirMusica.addActionListener(e -> AbrirMusica());

        JButton anterior = new JButton("<<");
        control.add(anterior);

        play = new JButton("▶ Play");
        control.add(play);

        play.addActionListener(e ->alternarPlayPause());

        JButton seguinte = new JButton(">>");
        control.add(seguinte);

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

            musicaAtual = new Musica(
                    musicas.getSelectedFile().getName(),
                    musicas.getSelectedFile().getPath()
            );

            musica.setText(musicaAtual.getNome());

            reprodutor.tocar(musicaAtual);
            play.setText("⏸ Pause");
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

    private JLabel musica;
    private JLabel artista;
    private JLabel capa;
    private Musica musicaAtual;
    private String caminhoMusica;
    private Player reprodutor = new Player();
    private JButton play;

}
