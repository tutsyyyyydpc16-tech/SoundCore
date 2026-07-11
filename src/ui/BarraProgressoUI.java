package ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class BarraProgressoUI extends BasicSliderUI {

    public BarraProgressoUI(JSlider slider) {
        super(slider);
    }

    @Override
    public void paintTrack(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(new Color(50,50,60));

        g2.fillRoundRect(
                trackRect.x,
                trackRect.y + trackRect.height / 2 - 3,
                trackRect.width,
                6,
                6,
                6);

        g2.setColor(new Color(0,255,170));

        int progresso = thumbRect.x + thumbRect.width / 2 - trackRect.x;

        g2.fillRoundRect(
                trackRect.x,
                trackRect.y + trackRect.height / 2 - 3,
                progresso,
                6,
                6,
                6);
    }

    @Override
    public void paintThumb(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(Color.white);

        g2.fillOval(
                thumbRect.x,
                thumbRect.y,
                thumbRect.width,
                thumbRect.height
        );

    }

    @Override
    protected Dimension getThumbSize() {
        return new Dimension(14,14);
    }
}
