package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public class BarraTitulo extends JPanel {

    public BarraTitulo(JFrame janela) {

        setLayout(new BorderLayout());
        setBackground(FUNDO);
        setPreferredSize(new Dimension(0,32));
        setBorder(BorderFactory.createMatteBorder(0,0,2,0, BORDA));

        JLabel titulo = new JLabel(" SoundCore");
        titulo.setForeground(TEXTO);
        titulo.setFont(FontePixel.obter(10));

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT,4,0));
        botoes.setOpaque(false);

        JButton minimizar = criarBotao("_");
        JButton fechar = criarBotao("X");

        minimizar.addActionListener(e -> janela.setState(Frame.ICONIFIED));
        fechar.addActionListener(e -> {
            janela.dispatchEvent(new java.awt.event.WindowEvent(janela, WindowEvent.WINDOW_CLOSING));
        });

        fechar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                fechar.setForeground(HOVER_FECHAR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                fechar.setForeground(TEXTO);
            }
        });

        botoes.add(minimizar);
        botoes.add(fechar);

        add(titulo, BorderLayout.WEST);
        add(botoes, BorderLayout.EAST);

        MouseAdapter arraste = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pontoInicial = e.getPoint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {

                Point pontoAtual = e.getLocationOnScreen();

                janela.setLocation(
                        pontoAtual.x - pontoInicial.x,
                        pontoAtual.y - pontoInicial.y
                );
            }
        };

        addMouseListener(arraste);
        addMouseMotionListener(arraste);
    }

    private JButton criarBotao(String texto) {

        JButton botao = new JButton(texto);
        botao.setFont(FontePixel.obter(9));
        botao.setForeground(TEXTO);
        botao.setBackground(FUNDO);
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(false);
        botao.setFocusPainted(false);
        botao.setPreferredSize(new Dimension(30,26));

        return botao;
    }

    private static final Color FUNDO = new Color(18,27,46);
    private static final Color TEXTO = new Color(0,255,170);
    private static final Color BORDA = new Color(0,255,170);
    private static final Color HOVER_FECHAR = new Color(255,70,70);

    private Point pontoInicial;
}
