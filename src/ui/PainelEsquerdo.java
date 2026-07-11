package ui;

import app.Janela;

import javax.swing.*;
import java.awt.*;


public class PainelEsquerdo extends JPanel{

    public PainelEsquerdo(Janela janela) {

        this.janela = janela;

        setPreferredSize(new Dimension(350, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Image imagem = new ImageIcon("Resource/Disco.png").getImage();

        Image imagemGrande = imagem.getScaledInstance(
                250, 250,
                Image.SCALE_SMOOTH
        );

        disco = new JLabel(new ImageIcon(imagemGrande));
        disco.setAlignmentX(Component.CENTER_ALIGNMENT);

        musica = new JLabel("Nenhuma música");
        musica.setAlignmentX(Component.CENTER_ALIGNMENT);

        artista = new JLabel("Nenhum artista");
        artista.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(30));
        add(disco);
        add(Box.createVerticalStrut(20));
        add(musica);
        add(artista);

        criarVolume();

    }

    private void criarVolume() {

        JPanel painelVolume = new JPanel(new BorderLayout());
        painelVolume.setBackground(FUNDO);

        JPanel linhaTopo = new JPanel(new BorderLayout());
        linhaTopo.setBackground(FUNDO);

        JLabel texto = new JLabel("VOL");
        texto.setForeground(TEXTO);
        texto.setFont(new Font("Consolas", Font.BOLD, 12));

        JLabel valorLabel = new JLabel("70");
        valorLabel.setForeground(TEXTO);
        valorLabel.setFont(new Font("Consolas", Font.BOLD, 12));

        linhaTopo.add(texto, BorderLayout.WEST);
        linhaTopo.add(valorLabel, BorderLayout.EAST);

        volumeBarras = new VolumeBarras();
        volumeBarras.setAoMudar(v -> {
            valorLabel.setText(String.valueOf(v));
            janela.alterarVolume(v);
        });

        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        wrapper.setBackground(FUNDO);
        wrapper.add(volumeBarras);

        painelVolume.add(linhaTopo, BorderLayout.NORTH);
        painelVolume.add(wrapper, BorderLayout.CENTER);
        painelVolume.setBorder(BorderFactory.createEmptyBorder(10, 15, 10,15));

        add(painelVolume);
    }

    private Janela janela;

    private JLabel musica;
    private JLabel artista;
    private JLabel disco;

    private VolumeBarras volumeBarras;

    private static final Color PAINEL = new Color(18, 27, 46);
    private static final Color FUNDO = new Color(14, 20, 34);
    private static final Color TEXTO = new Color(0 , 255, 170);
}
