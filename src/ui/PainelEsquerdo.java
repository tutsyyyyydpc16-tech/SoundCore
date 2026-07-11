package ui;

import app.Janela;

import javax.swing.*;
import java.awt.*;


public class PainelEsquerdo extends JPanel{

    public PainelEsquerdo() {

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

        JLabel texto = new JLabel("VOL");
        texto.setForeground(TEXTO);

        volume = new JSlider(0, 100, 70);

        painelVolume.add(texto, BorderLayout.NORTH);
        painelVolume.add(volume, BorderLayout.CENTER);

        volume.addChangeListener(e -> {
            janela.alterarVolume(volume.getValue());
        });

        add(painelVolume, BorderLayout.SOUTH);

    }

    private Janela janela;

    private JLabel musica;
    private JLabel artista;
    private JLabel disco;

    private JSlider volume;

    private static final Color PAINEL = new Color(18, 27, 46);
    private static final Color FUNDO = new Color(14, 20, 34);
    private static final Color TEXTO = new Color(0 , 255, 170);
}
