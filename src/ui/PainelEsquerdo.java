package ui;

import app.Janela;

import javax.swing.*;
import java.awt.*;


public class PainelEsquerdo extends JPanel{

    public PainelEsquerdo(Janela janela) {

        this.janela = janela;

        setPreferredSize(new Dimension(350, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        fundoImagem = new ImageIcon("Resource/FundoCapa.png").getImage();

        JLayeredPane camadaDisco = criarCamadaDisco();
        camadaDisco.setAlignmentX(Component.CENTER_ALIGNMENT);

        musica = new JLabel("Nenhuma música");
        musica.setAlignmentX(Component.CENTER_ALIGNMENT);

        artista = new JLabel("Nenhum artista");
        artista.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(musica);
        add(artista);
        add(camadaDisco);
        add(Box.createVerticalStrut(30));
        add(Box.createVerticalStrut(20));

        criarVolume();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (fundoImagem != null) {

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
            g2.drawImage(fundoImagem, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void atualizarCapa(byte[] bytesCapa) {

        final int TAMANHO = 250;

        if (bytesCapa != null) {
            ImageIcon icone = new ImageIcon(bytesCapa);
            capa.setImagem(icone.getImage());
            System.out.println("Tamanho da capa (label): " + capa.getWidth() + "x" + capa.getHeight());
            System.out.println("Tamanho da imagem original: " + icone.getIconWidth() + "x" + icone.getIconHeight());
        }
        else {
            capa.setImagem(null);
        }
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
        wrapper.setOpaque(false);
        wrapper.add(volumeBarras);

        painelVolume.add(linhaTopo, BorderLayout.NORTH);
        painelVolume.add(wrapper, BorderLayout.CENTER);
        painelVolume.setBorder(BorderFactory.createEmptyBorder(10, 15, 10,15));

        add(painelVolume);
    }

    private JLayeredPane criarCamadaDisco() {

        final int TAMANHO = 250;

        JLayeredPane camada = new JLayeredPane();
        camada.setPreferredSize(new Dimension(TAMANHO, TAMANHO));
        camada.setMaximumSize(new Dimension(TAMANHO, TAMANHO));

        capa = new PainelImagem();
        capa.setBounds(0, 0, TAMANHO, TAMANHO);
        camada.add(capa, Integer.valueOf(0));

        Image imagemDisco = new ImageIcon("Resource/Disco.png").getImage();
        Image discoEscalado = imagemDisco.getScaledInstance(TAMANHO, TAMANHO, Image.SCALE_SMOOTH);

        disco = new JLabel(new ImageIcon(discoEscalado));
        disco.setBounds(0, 0, TAMANHO, TAMANHO);
        camada.add(disco, Integer.valueOf(1));

        return camada;
    }

    private Janela janela;

    private PainelImagem capa;

    private JLabel musica;
    private JLabel artista;
    private JLabel disco;

    private VolumeBarras volumeBarras;

    private static final Color PAINEL = new Color(18, 27, 46);
    private static final Color FUNDO = new Color(14, 20, 34);
    private static final Color TEXTO = new Color(0 , 255, 170);

    private Image fundoImagem;
}
