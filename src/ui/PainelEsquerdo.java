package ui;

import app.Janela;

import javax.swing.*;
import java.awt.*;


public class PainelEsquerdo extends JPanel{

    public PainelEsquerdo(Janela janela) {

        this.janela = janela;

        setPreferredSize(new Dimension(350, 0));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createLineBorder(TEXTO, 2));

        fundoImagem = new ImageIcon("Resource/FundoCapa.png").getImage();

        JLayeredPane camadaDisco = criarCamadaDisco();
        camadaDisco.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel caixaInfo = criarCaixaInfo();
        caixaInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(30));
        add(camadaDisco);
        add(Box.createVerticalStrut(15));
        add(caixaInfo);

        Equalizer();
        criarVolume();

        //add(Box.createVerticalGlue());

        revalidate();
        repaint();
    }

    public JPanel criarCaixaInfo() {

        JPanel caixa = new JPanel();
        caixa.setLayout(new BoxLayout(caixa, BoxLayout.Y_AXIS));
        caixa.setBackground(CAIXA_FUNDO);
        caixa.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TEXTO, 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        caixa.setAlignmentX(Component.CENTER_ALIGNMENT);
        caixa.setMaximumSize(new Dimension(360, 70));

        musica = new JLabel("Nenhuma música");
        musica.setAlignmentX(Component.LEFT_ALIGNMENT);
        musica.setForeground(NEON_VERDE);
        musica.setFont(FontePixel.obter(9));

        artista = new JLabel("Nenhum artista");
        artista.setAlignmentX(Component.LEFT_ALIGNMENT);
        artista.setForeground(NEON_AZUL);
        artista.setFont(FontePixel.obter(9));

        caixa.add(musica);
        caixa.add(artista);

        return caixa;
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

    public void Equalizer() {

        JLabel labelEqualizer = new JLabel("EQUALIZER");
        labelEqualizer.setForeground(TEXTO_SECUNDARIO);
        labelEqualizer. setFont(FontePixel.obter(7));
        labelEqualizer.setAlignmentX(Component.LEFT_ALIGNMENT);

        equalizer = new EqualizerBarras();
        equalizer.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel painelEqualizer = new JPanel();
        painelEqualizer.setLayout(new BoxLayout(painelEqualizer, BoxLayout.Y_AXIS));
        painelEqualizer.setBackground(CAIXA_FUNDO);
        painelEqualizer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0,2,2,2, TEXTO),
                BorderFactory.createEmptyBorder(8,15,8,15)
        ));
        painelEqualizer.add(labelEqualizer);
        painelEqualizer.add(Box.createVerticalStrut(6));
        painelEqualizer.add(equalizer);
        painelEqualizer.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelEqualizer.setMaximumSize(new Dimension(360, 70));

        add(painelEqualizer);
    }

    public void atualizarCapa(byte[] bytesCapa) {

        final int TAMANHO = 250;

        if (bytesCapa != null) {
            ImageIcon icone = new ImageIcon(bytesCapa);
            capa.setImagem(icone.getImage());
            capa.revalidate();
            capa.repaint();
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
        painelVolume.setMaximumSize(new Dimension(360, 100));
        painelVolume.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TEXTO, 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JPanel linhaTopo = new JPanel(new BorderLayout());
        linhaTopo.setBackground(FUNDO);

        JLabel texto = new JLabel("VOL");
        texto.setForeground(TEXTO);
        texto.setFont(FontePixel.obter(12));

        JLabel valorLabel = new JLabel("70");
        valorLabel.setForeground(TEXTO);
        valorLabel.setFont(FontePixel.obter(12));

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

        add(painelVolume);

        add(criarBotaoBiblioteca());
    }

    private JButton criarBotaoBiblioteca() {

        JButton botao = new JButton("BIBLIOTECA");
        botao.setFont(FontePixel.obter(9));
        botao.setForeground(TEXTO);
        botao.setBackground(new Color(18,27,46));
        botao.setBorderPainted(true);
        botao.setBorder(BorderFactory.createMatteBorder(0,2,2,2,TEXTO));
        botao.setFocusPainted(false);
        botao.setOpaque(true);
        botao.setMargin(new Insets(0,0,0,0));
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setMaximumSize(new Dimension(350,30));
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        botao.addActionListener(e -> janela.abrirBiblioteca());

        return botao;
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

        disco = new PainelDisco(discoEscalado);
        disco.setBounds(0, 0, TAMANHO, TAMANHO);
        camada.add(disco, Integer.valueOf(1));

        return camada;
    }

    public PainelDisco getDisco() {
        return disco;
    }

    public EqualizerBarras getEqualizer() {
        return equalizer;
    }

    public void atualizarInfo(String nomeMusica, String nomeArtista) {

        musica.setText(nomeMusica != null ? nomeMusica : "Nenhuma música");

        if (nomeArtista == null || nomeArtista.isBlank()) {
            nomeArtista = "Nenhum artista";
        }

        artista.setText(nomeArtista);

        musica.setFont(FontePixel.obter(10));

        if (artista.getFont().canDisplayUpTo(nomeArtista) == -1) {
            artista.setFont(FontePixel.obter(10));
        }
        else {
            artista.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
    }

    private Janela janela;

    private PainelImagem capa;

    private JLabel musica;
    private JLabel artista;

    private VolumeBarras volumeBarras;

    private static final Color PAINEL = new Color(18, 27, 46);
    private static final Color FUNDO = new Color(14, 20, 34);
    private static final Color TEXTO = new Color(0 , 255, 170);
    private static final Color CAIXA_FUNDO = new Color(10, 25, 15, 220);
    private static final Color NEON_VERDE = new Color(0, 255,130);
    private static final Color NEON_AZUL = new Color(80, 190,255);
    private static final Color TEXTO_SECUNDARIO = new Color(150,160,175);

    private Image fundoImagem;

    private EqualizerBarras equalizer;

    private PainelDisco disco;
}
