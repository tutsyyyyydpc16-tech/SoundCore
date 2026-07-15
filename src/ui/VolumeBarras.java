package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.IntConsumer;

public class VolumeBarras extends JComponent {

    public VolumeBarras() {

        setPreferredSize(new Dimension(TOTAL_BARRAS * (LARGURA_BARRA + ESPACO_BARRA), ALTURA_MAX));
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        MouseAdapter arraste = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                atualizarPorClique(e.getX());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                atualizarPorClique(e.getX());
            }
        };

        addMouseListener(arraste);
        addMouseMotionListener(arraste);
    }

    private void atualizarPorClique(int x) {

        int novoValor = (int) ((x / (double) getWidth()) * 100);
        novoValor = Math.max(0, Math.min(100, novoValor));

        setValor(novoValor);

        if (aoMudar != null) {
            aoMudar.accept(novoValor);
        }
    }

    public void setValor(int valor) {
        this.valor = Math.max(0, Math.min(100, valor));
        repaint();
    }

    public int getValor() {
        return valor;
    }

    public void setAoMudar(IntConsumer listener) {
        this.aoMudar = listener;
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int barrasAcessas = (int) Math.round((valor / 100.0) * TOTAL_BARRAS);

        for (int i = 0; i < TOTAL_BARRAS; i++) {

            int x = i * (LARGURA_BARRA + ESPACO_BARRA);

            int altura = (int) (ALTURA_MAX * (0.4 + 0.6 * (i / (double) TOTAL_BARRAS)));
            int y = ALTURA_MAX - altura;

            if (i < barrasAcessas) {
                g2.setColor(i >= TOTAL_BARRAS - 3 ? COR_AMARELA : COR_VERDE);
            }
            else {
                g2.setColor(COR_VAZIA);
            }

            g2.fillRoundRect(x, y, LARGURA_BARRA, altura, 2, 2);
        }
    }

    private static final int TOTAL_BARRAS = 20;
    private static final int LARGURA_BARRA = 6;
    private static final int ESPACO_BARRA = 3;
    private static final int ALTURA_MAX = 24;

    private static final Color COR_VAZIA = new Color(40,45,55);
    private static final Color COR_VERDE = new Color(0,255,130);
    private static final Color COR_AMARELA = new Color(255,210,0);

    private int valor = 70;
    private IntConsumer aoMudar;
}
