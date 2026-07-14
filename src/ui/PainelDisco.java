package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PainelDisco extends JPanel {

    public PainelDisco(Image imagem) {

        this.disco = imagem;

        setOpaque(false);

        timer = new Timer(16, e -> {
            angulo += 1;
            repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.rotate(Math.toRadians(angulo),
                getWidth()/2.0,
                getHeight()/2.0);

        g2.drawImage(
                disco,
                0,
                0,
                getWidth(),
                getHeight(),
                null);

        g2.dispose();

    }

    public void iniciarAnimacao() {
        timer.start();
    }

    public void pararAnimacao() {
        timer.stop();
    }

    private Image disco;
    private double angulo = 0;

    private Timer timer;
}
