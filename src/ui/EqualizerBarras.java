package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class EqualizerBarras extends JComponent {

    public EqualizerBarras() {
        setPreferredSize(new Dimension(TOTAL_BARRAS * (LARGURA_BARRA + ESPACO_BARRA), ALTURA_MAX));
        setOpaque(true);
        setBackground(new Color(10,25,15));

        zerarAlturas();

        timer= new Timer(120, e -> {
            atualizarAlturas();
            redesenharBuffer();
            repaint();
        });
    }

    public void setAtivo(boolean ativo) {

        this.ativo = ativo;

        if (ativo) {
            timer.start();
        }
        else {
            timer.stop();
            zerarAlturas();
            redesenharBuffer();
            repaint();
        }
    }

    private void atualizarAlturas() {
        for (int i = 0; i < TOTAL_BARRAS; i++) {
            alturas[i] = 2 + random.nextInt(ALTURA_MAX -2);
        }
    }

    private void zerarAlturas() {
        for (int i = 0; i < TOTAL_BARRAS; i++) {
            alturas[i] = 2;
        }
    }

    private void redesenharBuffer() {

        int largura = getWidth();
        int altura = getHeight();

        if (largura <= 0 || altura <= 0) {
            return;
        }

        if (buffer == null || buffer.getWidth() != largura || buffer.getHeight() != altura) {
            buffer = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
        }

        Graphics2D g2 = buffer.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setComposite(AlphaComposite.Src);
        g2.setColor(getBackground());
        g2.fillRect(0,0, largura, altura);
        g2.setComposite(AlphaComposite.SrcOver);

        for (int i = 0; i < TOTAL_BARRAS; i++) {

            int x = i * (LARGURA_BARRA + ESPACO_BARRA);
            int alturaBarra = alturas[i];
            int y = ALTURA_MAX - alturaBarra;

            g2.setColor(CORES[i % CORES.length]);
            g2.fillRoundRect(x,y,LARGURA_BARRA, alturaBarra,2,2);
        }

        g2.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {

        if (buffer == null) {
            redesenharBuffer();
        }

        if (buffer != null) {
            g.drawImage(buffer,0,0,this);
        }
    }

    private static final Color[] CORES = {
            new Color(0,255,170),
            new Color(0,200,255),
            new Color(150,100,255),
            new Color(255,80,200)
    };

    private final int[] alturas = new int[TOTAL_BARRAS];
    private final Random random = new Random();
    private Timer timer;
    private boolean ativo = false;
    private BufferedImage buffer;

    private static final int TOTAL_BARRAS = 24;
    private static final int LARGURA_BARRA = 4;
    private static final int ESPACO_BARRA = 3;
    private static final int ALTURA_MAX = 30;

}
