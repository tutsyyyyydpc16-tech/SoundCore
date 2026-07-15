package ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class ScrollBarEscura extends BasicScrollBarUI {

    private static final Color TRILHO = new Color(14,20,34);
    private static final Color POLEGAR = new Color(0,150,110);
    private static final Color POLEGAR_HOVER = new Color(0,255,170);

    @Override
    protected void configureScrollBarColors() {
        this.trackColor = TRILHO;
        this.thumbColor = POLEGAR;
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return criarBotaoInvisivel();
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return criarBotaoInvisivel();
    }

    private JButton criarBotaoInvisivel() {
        JButton botao = new JButton();
        botao.setPreferredSize(new Dimension(0,0));
        botao.setMinimumSize(new Dimension(0,0));
        botao.setMaximumSize(new Dimension(0,0));

        return botao;
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(TRILHO);
        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {

        if (thumbBounds.isEmpty()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(isThumbRollover() ? POLEGAR_HOVER : POLEGAR);
        g2.fillRoundRect(
                thumbBounds.x + 2,
                thumbBounds.y +2,
                thumbBounds.width - 4,
                thumbBounds.height - 4,
                6,6
        );

        g2.dispose();
    }
}
