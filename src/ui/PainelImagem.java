package ui;

import javax.swing.*;
import java.awt.*;

public class PainelImagem extends JComponent {

    public void setImagem(Image imagem) {
        this.imagem = imagem;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (imagem != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

            g2.drawImage(imagem, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private Image imagem;
}
